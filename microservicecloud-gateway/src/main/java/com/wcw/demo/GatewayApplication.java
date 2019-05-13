package com.wcw.demo;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(value = {"com.wcw.demo.config"})

public class GatewayApplication {

    @Value("${hostUrl}")
    private static String hostUrl;

    public static void main(String[] args) {
        System.out.println(hostUrl);
        SpringApplication.run(GatewayApplication.class, args);
    }


}
