package com.example.rabbitMQ;

import com.example.rabbitMQ.rabbitMQ.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RabbitMQMessageTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void testSendMessageAndCheckDlx() throws InterruptedException {
        String testOrderId = "testOrder123";
        Message message = rabbitTemplate.getMessageConverter().toMessage(testOrderId, new MessageProperties());

        // 设置消息TTL为1秒
        message.getMessageProperties().setExpiration("1000");

        // 发送消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, "order", message);

        // 等待足够长的时间，让消息过期并转移到死信队列
        Thread.sleep(10000); // 等待5秒确保消息已经被转移到死信队列

        // 尝试从死信队列接收消息
        Message dlxMessage = rabbitTemplate.receive(RabbitMQConfig.DLX_ORDER_QUEUE, 5000); // 等待最多5秒

        // 验证死信队列接收到了消息
        assertNotNull(dlxMessage, "DLX should have received the expired message.");
        String dlxMessageBody = new String(dlxMessage.getBody());
        assertTrue(dlxMessageBody.contains(testOrderId), "The DLX message should contain the test order ID.");
    }
}
