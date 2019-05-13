package com.wcw.job.ShopSyncJob;

import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.wcw.job.ShopSyncJob.config.ElasticJobBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author wuchaowu
 * @date 2019/5/1013:52
 */
@Configuration
public class TaskConfigure {
    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private ShopSyncJob shopSyncJob;

   @Value("${job.pinganshopsyncjob.cron}")
   private String cronExp;

    @Bean(initMethod = "init")
    public SpringJobScheduler syncShop() {
        return ElasticJobBuilder.getInstance(regCenter).setJobName("ShopSyncJob")
                .setJobTask(shopSyncJob).setShardingTotalCount(1).setCronExpression(cronExp).build();
    }


}