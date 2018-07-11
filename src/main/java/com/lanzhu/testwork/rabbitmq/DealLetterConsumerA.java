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
public class DealLetterConsumerA {

    @Autowired
    private MQConfig mqConfig;

    @PostConstruct
    public void initConsumer() {
        try {
            Connection connection = mqConfig.getConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(MQConfig.DEAL_EXCHANGE, MQConfig.TOPIC_TYPE);

            //声明一个队列，接受死信交换器接受过来的路由为message.a 的消息
            channel.queueDeclare("DEAL_A_QUEUE", false, false, false, null);
            //把队列绑定到交换器DEAL_EXCHANGE
            channel.queueBind("DEAL_A_QUEUE", MQConfig.DEAL_EXCHANGE, MQConfig.A_ROUTE_KEY);
            //绑定一个消费者，接受处理死信A队列的消息
            channel.basicQos(1); //一次接受一个消息
            channel.basicConsume("DEAL_A_QUEUE", false, new ConsumerA(channel));


        }catch (Exception e) {
            log.error("Init deal letter consumer A fail.", e);
        }
    }

    class ConsumerA extends DefaultConsumer{
        private Channel channel;
        public ConsumerA(Channel channel) {
            super(channel);
            this.channel = channel;
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            try {
                MessageA messageA = JSON.parseObject(body, MessageA.class);
                log.info("=======> Consumer A get a message: {}", messageA == null ?  "null" : messageA.toString());
                Thread.sleep(40000); //休眠40秒
            }catch (Exception e) {
                log.error("Consumer B handle message have exception ", e);
            }finally {
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        }
    }
}
