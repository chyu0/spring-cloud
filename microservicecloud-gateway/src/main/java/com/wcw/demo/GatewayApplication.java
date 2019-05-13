package com.wcw.demo;



import filter.MyAddRequestParameterGatewayFilterFactory;
import filter.MyTokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GatewayApplication {

    @Value("${hostUrl}")
    private static String hostUrl;




    public static void main(String[] args) {
        System.out.println(hostUrl);

        SpringApplication.run(GatewayApplication.class, args);
    }
    @Bean
    public MyTokenFilter tokenFilter() {
        final MyTokenFilter myTokenFilter = new MyTokenFilter();
        return myTokenFilter;
    }
    @Bean
    public MyAddRequestParameterGatewayFilterFactory addRequestParameterGatewayFilterFactory(){
        return new MyAddRequestParameterGatewayFilterFactory();
    }


}
