package rabbitmq;


import com.lanzhu.testwork.rabbitmq.Message;
import com.lanzhu.testwork.rabbitmq.MessageA;
import org.junit.Test;

import java.util.Date;

public class MessageTest {


    @Test
    public void testToString() {
        Message message = new MessageA();
        message.setInfo("12123");
        message.setId(1);
        message.setTtl(123L);
        message.setCreateTime(new Date());

        System.out.println(message.toString());
    }
}
