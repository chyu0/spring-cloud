package com.wcw.mq.shopsync;



import com.wcw.mq.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuchaowu
 * @date 2019/5/109:16
 */
@Configuration
public class RabbitMQConfig {





    @Bean
    public Queue topicQueue() {
        return new Queue(GlobalConstant.SHOP_QUEUE_NAME, true);
    }

    @Bean
    public CustomExchange CustomExchange() {
//        Map<String, Object> args = new HashMap<>();
//        args.put("x-delayed-type", "direct");
        return new CustomExchange(GlobalConstant.SHOP_QUEUE_EXCHANGE_NAME ,"x-delayed-message", true, false, null);
    }

    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(topicQueue()).to(CustomExchange()).with(GlobalConstant.ROUTINGKEY).noargs();
    }


}
