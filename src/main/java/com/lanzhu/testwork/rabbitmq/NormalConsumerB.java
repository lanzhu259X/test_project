package com.lanzhu.testwork.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于模拟队列到期时间生产的死信消息
 */
@Slf4j
@Component
public class NormalConsumerB {

    @Autowired
    private MQConfig mqConfig;

    @PostConstruct
    public void initConsumer() {
        try {
            Connection connection = mqConfig.getConnection();

            //声明一个直连模式的exchange
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(MQConfig.DEAL_EXCHANGE_B, MQConfig.DIRECT_TYPE);

            //声明正常的交换器
            channel.exchangeDeclare(MQConfig.EXCHANGE, MQConfig.TOPIC_TYPE);

            //声明一个队列，接受全部的消息类型，当出现私信时，把死信转到交换器MQConfig.DEAL_EXCHANGE
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("x-expires", 120000); //设置为两分钟后到期
            map.put("x-dead-letter-exchange", MQConfig.DEAL_EXCHANGE_B); //设置死信消息去向

            channel.queueDeclare("ALL.MESSAGE_QUEUE.B", false, false, false, map);
            // 绑定队列到交换器和监听的的路由
            channel.queueBind("ALL.MESSAGE_QUEUE.B", MQConfig.EXCHANGE, MQConfig.ALL_ROUTE_KEY);
            //绑定具体的消费者
            channel.basicQos(1); //一次接受一个消息
            channel.basicConsume("ALL.MESSAGE_QUEUE.B", false, new Consumer(channel));

            log.info("-----------> Init Normal consumer success.");

        }catch (Exception e) {
            log.error("Init Normal Consumer fail.", e);
        }
    }

    class Consumer extends DefaultConsumer {
        private Channel channel;

        public Consumer(Channel channel) {
            super(channel);
            this.channel = channel;
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            try {
                Message message = JSON.parseObject(body, Message.class);
                log.info("=======> Consumer get a message: {}", message == null ?  "null" : JSON.toJSONString(message));
                Thread.sleep(50000); //休眠50秒
            }catch (Exception e) {
                log.error("Consumer B handle message have exception ", e);
            }finally {
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        }
    }
}
