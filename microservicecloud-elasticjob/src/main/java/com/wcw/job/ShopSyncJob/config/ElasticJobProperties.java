package com.wcw.job.ShopSyncJob.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "elastic.job")
public class ElasticJobProperties {

    private boolean                    enable     = false;

    private String                     zkConnectString;

    private String                     zkBasePath = "enterprise/elastic-job";

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getZkConnectString() {
        return zkConnectString;
    }

    public void setZkConnectString(String zkConnectString) {
        this.zkConnectString = zkConnectString;
    }

    public String getZkBasePath() {
        return zkBasePath;
    }

    public void setZkBasePath(String zkBasePath) {
        this.zkBasePath = zkBasePath;
    }

}
