package com.wcw.demo.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.net.URL;


/**
 * @author wuchaowu
 * @date 2019/5/89:22
 */
@Component
public class RealPath implements GatewayFilter, Ordered {

    private String redirectUrl;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request =    exchange.getRequest();
        MultiValueMap<String, String> params =  request.getQueryParams();
        String method =  params.getFirst("method");
        URI uri = exchange.getRequest().getURI();
        URL urlObj = null;
        String originalQuery = uri.getQuery();
        String url =  uri.getScheme()+"://"+uri.getHost()+":"+uri.getPort();
        redirectUrl = url+"/"+method;
        ServerHttpResponse response = exchange.getResponse();
        //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
        return response.setComplete();


//                URI newUri = UriComponentsBuilder.fromUri(uri)
//                        .replaceQuery("")
//                        .build(true)
//                        .toUri();




    }

    @Override
    public int getOrder() {
        return 0;
    }

    public String getUrl() {
       return redirectUrl;
    }
}
