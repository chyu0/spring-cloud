package com.wcw.mq.shopsync;

/**
 * @author wuchaowu
 * @date 2019/5/109:02
 */

import com.wcw.mq.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


@Component
public class ShopSyncConsumer  {
    private static final Logger logger = LoggerFactory.getLogger(ShopSyncConsumer.class);

    /**
     * 门店同步消息处理
     *
     * @param body 接收的二进制数据
     */
    @RabbitListener(queues = GlobalConstant.SHOP_QUEUE_NAME)
    public void OnRecivedAsync( byte[] body) throws UnsupportedEncodingException {
        String message = new String(body, "UTF-8");

        logger.info("接收消息:{}",message);


    }

}
