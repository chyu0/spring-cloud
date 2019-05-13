package com.wcw.job.ShopSyncJob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wuchaowu
 * @date 2019/5/1011:37
 */
@Component
public class ShopSyncJob implements SimpleJob {
    private final Logger log   = LoggerFactory.getLogger(ShopSyncJob.class);

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info(String.format("Item: %s | Time: %s | Thread: %s | %s",
                shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "SIMPLE"));
    }
}
