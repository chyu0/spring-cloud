package com.wcw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.wcw.myrule.MySelfRule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value = {"com.wcw.myrule","com.wcw.job.ShopSyncJob","com.wcw.mq.shopsync"})
//在启动该微服务的时候就能去加载我们的自定义Ribbon配置类，从而使配置生效
@RibbonClient(name="MICROSERVICECLOUD-SERVICE",configuration=MySelfRule.class)
@EnableScheduling
@EnableDiscoveryClient
public class Consumer80_App
{
	public static void main(String[] args)
	{
		SpringApplication.run(Consumer80_App.class, args);
	}
}
