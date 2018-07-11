package com.lanzhu.testwork.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class NormalConsumer {

    @Autowired
    private MQConfig mqConfig;

    @PostConstruct
    public void initConsumer() {

        try {
            Connection connection = mqConfig.getConnection();

            //声明一个接受死信的交换器
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(MQConfig.DEAL_EXCHANGE, MQConfig.TOPIC_TYPE);

            //声明正常的交换器
            channel.exchangeDeclare(MQConfig.EXCHANGE, MQConfig.TOPIC_TYPE);

            //声明一个队列，接受全部的消息类型，当出现私信时，把死信转到交换器MQConfig.DEAL_EXCHANGE
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("x-dead-letter-exchange", MQConfig.DEAL_EXCHANGE);

            channel.queueDeclare("ALL.MESSAGE_QUEUE", false, false, false, map);
            // 绑定队列到交换器和监听的的路由
            channel.queueBind("ALL.MESSAGE_QUEUE", MQConfig.EXCHANGE, MQConfig.ALL_ROUTE_KEY);
            //不绑定具体的消费者

            log.info("-----------> Init Normal consumer success.");

        }catch (Exception e) {
            log.error("Init Normal Consumer fail.", e);
        }
    }
}
