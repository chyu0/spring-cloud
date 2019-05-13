package com.wcw.demo.config;

/**
 * @author wuchaowu
 * @date 2019/5/717:38
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态加载路由配置
 *
 * @author Gsealy
 * @since 2018-10-17 14:07:24
 */
@Component
public class RefreshRouteLocator implements RouteLocator {

    private RouteLocatorBuilder builder;
    private RouteLocatorBuilder.Builder routesBuilder;
    private Flux<Route> route;

    @Autowired
    com.wcw.demo.filter.GatewayRoutesRefresher gatewayRoutesRefresher;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    public RefreshRouteLocator(RouteLocatorBuilder builder) {
        this.builder = builder;
        clearRoutes();
    }

    public void clearRoutes() {
        routesBuilder = builder.routes();
    }
    /***
     * convert request query string to map
     *
     * @param queryString
     * @return
     */
    private static Map<String, String> parseQueryString(String queryString) {
        if (StringUtils.isEmpty(queryString)) {
            return null;
        }
        Map<String, String> argMap = new HashMap<String, String>();
        String[] queryArr = queryString.split("&");
        for (int i = 0; i < queryArr.length; i++) {
            String string = queryArr[i];
            String keyAndValue[] = string.split("=", 2);
            if (keyAndValue.length != 2) {
                argMap.put(keyAndValue[0], "");
            } else {
                argMap.put(keyAndValue[0], keyAndValue[1]);
            }
        }
        return argMap;
    }

    private static Boolean isExistParams(String Url){
        int index = Url.indexOf("?");
        if (index != -1) {
          return true;
        }
        return false;

    }

    /**
     * 使用RouteLocatorBuilder.Builder创建新的路由规则（ps.仅支持添加最基础的转发规则）
     *
     * @param id 路由id
     * @param path 路由path
     * @param uri 指向地址
     * @return RefreshRouteLocator
     */
    @NotNull
    public RefreshRouteLocator addRoute(@NotNull final String id, @NotNull final String path,
                                        @NotNull final URI uri) throws URISyntaxException {
        if (StringUtils.isEmpty(uri.getScheme())) {
            throw new URISyntaxException("Missing scheme in URI: {}", uri.toString());
        }
        if(!StringUtils.isEmpty(uri.getQuery())){
            Map<String, String>  params =  parseQueryString(uri.getQuery());
            String   method =  params.get("method");
            try {
                final  URI    newUri  = new URL(uri.getScheme(),uri.getHost(),uri.getPort(),method+"?"+uri.getQuery()).toURI();
                //uri.getQuery()
                routesBuilder.route(id, fn -> fn
                        .query(path + "method")
                        .uri(newUri)
                );



            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        return this;
    }

    /**
     * 使用RouteDefinition添加路由节点，可自己配置相关属性
     *
     * @param definition 属性定义
     * @return RefreshRouteLocator
     */
    @NotNull
    public RefreshRouteLocator addRoute(@NotNull RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        return this;
    }

    /**
     * 配置完成后，调用本方法构建路由和刷新路由表
     */
    public void buildRoutes() {
        if (routesBuilder != null) {
            this.route = routesBuilder.build().getRoutes();
        }
        gatewayRoutesRefresher.refreshRoutes();
    }

    /**
     * @return 路由信息
     */
    @Override
    public Flux<Route> getRoutes() {
        return route;
    }
}