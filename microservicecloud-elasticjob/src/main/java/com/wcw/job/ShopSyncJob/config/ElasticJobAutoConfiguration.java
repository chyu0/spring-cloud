package com.wcw.job.ShopSyncJob.config;

/**
 * @author wuchaowu
 * @date 2019/5/1014:47
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@Configuration
@EnableConfigurationProperties(ElasticJobProperties.class)
public class ElasticJobAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(ZookeeperRegistryCenter.class)
    public ZookeeperRegistryCenter regCenter(ElasticJobProperties elasticJobProperties) {
        ZookeeperRegistryCenter center = new ZookeeperRegistryCenter(new ZookeeperConfiguration(elasticJobProperties.getZkConnectString(),
                elasticJobProperties.getZkBasePath()));
        center.init();
        return center;
    }

}
