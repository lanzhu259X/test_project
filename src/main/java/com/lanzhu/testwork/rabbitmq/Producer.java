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
            Long randomNum = 30000L + RandomUtils.nextLong(0L, 30000L); //有效时长为30到60秒
            i--;
            //生产一个MessageA
            MessageA messageA = new MessageA();
            messageA.setId(i);
            messageA.setInfo("MessageA_" + i);
            messageA.setTtl(randomNum);
            messageA.setCreateTime(new Date());

            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .expiration(String.valueOf(randomNum)) //设置消息的有效时长
                    .build();
            if (i == 5) {
                //如果是ID==5的，设置为永久存在类型的消息
                messageA.setTtl(null);
                properties = null;
            }
            log.info("========> Create a message: {}", messageA.toString());
            mqConfig.sendMQMessage("ProducerA", MQConfig.A_ROUTE_KEY, messageA, properties);
            try {
                //暂停5秒
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("--------------> Create message End.");
    }


}
