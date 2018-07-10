package rabbitmq;

import com.lanzhu.testwork.WorkApplication;
import com.lanzhu.testwork.rabbitmq.MQConfig;
import com.lanzhu.testwork.rabbitmq.Producer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WorkApplication.class)
public class RabbitmqTest {

    @Autowired
    private MQConfig mqConfig;
    @Autowired
    private Producer producer;

    @Test
    public void testMessageTTL() throws Exception {
        //测试消息的有效期

        //创建一个队列，用于存放生产者生产的消息，监听的路由规则为所有的message
        Connection connection = mqConfig.getConnection();
        Channel channelA = connection.createChannel();
        channelA.queueDeclare("A.MESSAGE_QUEUE", false, false, false, null); 

    }

}
