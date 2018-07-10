package com.lanzhu.testwork.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class MQConfig {

    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.port}")
    private int port;
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.virtual-host}")
    private String vhost;

    // topic exchange name
    public static final String TOPIC_TYPE = "topic"; // 消息分配模式
    public static final String EXCHANGE = "TEST-EXCHANGE"; //正常消息使用的交换器
    public static final String DEAL_EXCHANGE = "DEAL-EXCHANGE"; //死信消息使用是的交换交换器


    //建立全局唯一的链接器，和为每一个service bean 实例创建一个channel.
    private Connection connection;
    private Map<String, Channel> channelMap;

    //route key
    public static final String A_ROUTE_KEY = "message.a";
    public static final String B_ROUTE_KEY = "message.b";
    public static final String ALL_ROUTE_KEY = "message.#";


    /**
     * 在初始化Bean后先创建一个Rabbitmq的连接，供整个项目使用，不需要在每次使用是都创建一个，这样浪费资源
     * @return
     */
    @PostConstruct
    public Connection getConnection() {
        if (this.connection != null && this.connection.isOpen()) {
            return connection;
        }
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setVirtualHost(vhost);
            connection = factory.newConnection();
            return connection;
        }catch (Exception e) {
            log.error("init Rabbitmq connection fail. exception:{}", e.toString(), e);
            throw new IllegalStateException("Init Rabbitmq connection fail!");
        }
    }

    /**
     * 为一个Bean Service 创建一个连接的channel, 不需要每次发送一个消息，都重新创建一个channel
     * @param beanName
     * @return
     */
    private Channel getSendChannel(String beanName) {
        if (channelMap == null) {
            channelMap = new HashMap<String, Channel>();
        }
        try {
            Channel result = channelMap.get(beanName);
            if (result == null || !result.isOpen()) {
                result = getConnection().createChannel();
                /**
                 * 参数1：交换器名称
                 * 参数2：交换模式 topic
                 * 参数3：交换器持久性，如果为true则服务器重启时不会丢失
                 * 参数4：交换器在不被使用时是否删除
                 * 参数5：交换器的其他属性
                 */
                result.exchangeDeclare(MQConfig.EXCHANGE, MQConfig.TOPIC_TYPE, true, false, null);
                channelMap.put(beanName, result);
            }
            return result;
        }catch (Exception e) {
            log.error("get beanName:{} channel fail.", beanName, e);
            throw new IllegalStateException("get Send Message Rabbitmq Channel Fail.");
        }
    }


    /**
     * 统一发送消息的入口
     * @param beanName 调用发送消息的Bean Service Name, 用于查找使用同一个BeanService使用同一个Channel
     * @param routeKey 发送消息的路由
     * @param messageBody 消息体
     * @param properties 发送消息的配置信息(消息的头信息)
     * @param <T>
     */
    public <T> void sendMQMessage(String beanName, String routeKey, T messageBody, AMQP.BasicProperties properties) {
        try {
            Channel channel = getSendChannel(beanName);
            /**
             * 参数1：交换器名称
             * 参数2：路由 route key
             * 参数3：其他配置
             * 参数4：消息体(注意可以序列化)
             */
            channel.basicPublish(MQConfig.EXCHANGE, routeKey, properties, JSON.toJSONBytes(messageBody));
        }catch (Exception e) {
            log.error("Send an message to MQ fail. BeanName:{}; routeKey:{}; properties:{}; messageBody:{}", beanName, routeKey,
                    properties == null ? "null" : properties.toString(), JSON.toJSONString(messageBody), e);
            throw new IllegalStateException("Send an message to MQ fail.");
        }
    }


}
