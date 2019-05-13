package com.wcw.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuchaowu
 * @date 2019/5/716:37
 */
@Configuration
public class MyConfig {
    @Bean
    public com.wcw.demo.filter.MyTokenFilter tokenFilter() {
        final com.wcw.demo.filter.MyTokenFilter tokenFilter = new com.wcw.demo.filter.MyTokenFilter();
        return tokenFilter;
    }
    @Bean
    public com.wcw.demo.filter.MyAddRequestParameterGatewayFilterFactory addRequestParameterGatewayFilterFactory(){
        return new com.wcw.demo.filter.MyAddRequestParameterGatewayFilterFactory();
    }
    @Bean
    public com.wcw.demo.filter.RealPath getRealPath(){
        return new com.wcw.demo.filter.RealPath();
    }
}
