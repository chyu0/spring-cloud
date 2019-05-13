package com.wcw.mq;

/**
 * @author wuchaowu
 * @date 2019/5/109:04
 */
public class GlobalConstant {
    public static final String SHOP_QUEUE_NAME ="c.shopsync.refreshcache";
    public static final String SHOP_QUEUE_EXCHANGE_NAME = "topic.notificationExchage";
    public static final String ROUTINGKEY ="#.ShopChange.#" ;
}
