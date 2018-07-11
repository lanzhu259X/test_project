package com.lanzhu.testwork.rabbitmq;

import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 生产者A, 生成消息，发送到MQ中
 */
@Slf4j
@Service
public class Producer {

    @Autowired
    private MQConfig mqConfig;

    /**
     * 模拟生成消息放入到MQ中
     */
    public void createMQMessage() {
        int i = 10;
        while (i > 0) {
            i--;
            Long randomNum = 30000L + RandomUtils.nextLong(0L, 30000L); //有效时长为30到60秒
            //生产一个Message
            Message message = null;
            boolean isB = i % 3 == 0;
            if (isB) {
                message = new MessageB();
                message.setInfo("MessageB_" + i);
            }else {
                message = new MessageA();
                message.setInfo("MessageA_" + i);
            }
            message.setId(i);
            message.setTtl(randomNum);
            message.setCreateTime(new Date());

            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .expiration(String.valueOf(randomNum)) //设置消息的有效时长
                    .build();
            if (i == 5) {
                //如果是ID==5的，设置为永久存在类型的消息
                message.setTtl(null);
                properties = null;
            }
            log.info("========> Create a message: {}", message.toString());
            mqConfig.sendMQMessage("ProducerA", (isB ? MQConfig.B_ROUTE_KEY : MQConfig.A_ROUTE_KEY), message, properties);
            try {
                //暂停5秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("--------------> Create message End.");
    }
}
