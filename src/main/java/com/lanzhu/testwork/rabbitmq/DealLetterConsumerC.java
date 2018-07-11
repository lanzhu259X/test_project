package com.lanzhu.testwork.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Component
public class DealLetterConsumerC {

    @Autowired
    private MQConfig mqConfig;

    @PostConstruct
    public void initConsumer() {
        try {
            Connection connection = mqConfig.getConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(MQConfig.DEAL_EXCHANGE_B, MQConfig.DIRECT_TYPE);

            //声明一个队列，接受死信 DEAL_EXCHANGE_B 发过来的消息
            channel.queueDeclare("DEAL_C_QUEUE", false, false, false, null);
            //把队列绑定到交换器DEAL_EXCHANGE 路由为message.a的消息
            channel.queueBind("DEAL_C_QUEUE", MQConfig.DEAL_EXCHANGE_B, MQConfig.A_ROUTE_KEY);
            //绑定一个消费者，接受处理死信
            channel.basicQos(1); //一次接受一个消息
            channel.basicConsume("DEAL_C_QUEUE", false, new ConsumerC(channel));

        }catch (Exception e) {
            log.error("Init deal letter consumer C fail.", e);
        }
    }

    class ConsumerC extends DefaultConsumer{
        private Channel channel;
        public ConsumerC(Channel channel) {
            super(channel);
            this.channel = channel;
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            try {
                MessageA messageA = JSON.parseObject(body, MessageA.class);
                log.info("=======> Consumer C get a message: {}", messageA == null ?  "null" : messageA.toString());
            }catch (Exception e) {
                log.error("Consumer C handle message have exception ", e);
            }finally {
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        }
    }
}
