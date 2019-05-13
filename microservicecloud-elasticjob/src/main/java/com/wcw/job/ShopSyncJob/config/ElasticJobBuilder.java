package com.wcw.job.ShopSyncJob.config;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.commons.lang3.StringUtils;

/**
 * elasticJobs的工厂类
 * 
 * @author liuhaoyong 2017年7月7日 下午5:31:40
 */


public class ElasticJobBuilder {

    private ZookeeperRegistryCenter regCenter;

    private ElasticJob              elasticJob;

    private String                  jobName;

    private String                  cronExpression;

    private int                     shardingTotalCount;

    private String                  shardingItemParameters;

    private String                  jobDesc;

    public static ElasticJobBuilder getInstance(ZookeeperRegistryCenter regCenter) {
        return new ElasticJobBuilder(regCenter);
    }

    private ElasticJobBuilder(ZookeeperRegistryCenter regCenter) {
        this.regCenter = regCenter;
    }

    private ElasticJobBuilder() {
    }

    public ElasticJobBuilder setJobTask(ElasticJob task) {
        this.elasticJob = task;
        return this;
    }

    public ElasticJobBuilder setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
        return this;
    }

    public ElasticJobBuilder setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public ElasticJobBuilder setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public ElasticJobBuilder setShardingTotalCount(int shardingTotalCount) {
        this.shardingTotalCount = shardingTotalCount;
        return this;
    }

    public ElasticJobBuilder setShardingItemParameters(String shardingItemParameters) {
        this.shardingItemParameters = shardingItemParameters;
        return this;
    }

    /**
     * 构建job
     * 
     * @return
     */
    public SpringJobScheduler build() {
        assert regCenter != null;

        JobCoreConfiguration jobCoreConfig = JobCoreConfiguration
                .newBuilder(
                        (StringUtils.isNotBlank(this.jobName)) ? this.jobName : elasticJob.getClass().getSimpleName(),
                        cronExpression, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters).description(this.jobDesc).build();
        JobTypeConfiguration jobTypeConfig = null;
        if (this.elasticJob instanceof SimpleJob) {
            jobTypeConfig = new SimpleJobConfiguration(jobCoreConfig, elasticJob.getClass().getCanonicalName());
        } else if (this.elasticJob instanceof DataflowJob) {
            jobTypeConfig = new DataflowJobConfiguration(jobCoreConfig, elasticJob.getClass().getCanonicalName(), true);
        }

        LiteJobConfiguration liteJobConfig = LiteJobConfiguration.newBuilder(jobTypeConfig).overwrite(true).build();
        return new SpringJobScheduler(elasticJob, regCenter, liteJobConfig);
    }

}
