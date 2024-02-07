package com.example.rabbitMQ;

import com.example.rabbitMQ.rabbitMQ.OrderMessageListener;
import com.example.rabbitMQ.rabbitMQ.RabbitMQSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class RabbitMQSenderReceiverTest {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @MockBean
    private OrderMessageListener orderMessageListener;

    @Test
    public void testSendMessage() {
        String testOrderId = "12345";
        rabbitMQSender.sendOrder(testOrderId, 5000); // 发送消息，假设TTL为5秒

        // 休眠足够的时间以确保消息被处理
        try {
            Thread.sleep(1000); // 1秒应足够接收和处理消息
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 验证OrderMessageListener接收到了消息
        verify(orderMessageListener, timeout(1000)).onMessage(testOrderId);
    }
}
