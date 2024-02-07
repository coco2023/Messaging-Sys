package com.example.rabbitMQ;

import com.example.rabbitMQ.rabbitMQ.DlxOrderListener;
import com.example.rabbitMQ.rabbitMQ.RabbitMQSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class DlxOrderListenerTest {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @MockBean
    private DlxOrderListener dlxOrderListener;

    @Test
    public void testDlxMessage() {
        String testOrderId = "67890";
        rabbitMQSender.sendOrder(testOrderId, 1000); // 发送消息，假设TTL为1秒

        // 休眠足够的时间以确保消息过期并被转发到死信队列
        try {
            Thread.sleep(2000); // 2秒应足够消息过期和被处理
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 验证DlxOrderListener接收到了死信消息
        verify(dlxOrderListener, timeout(1000)).onDlxMessage(testOrderId);
    }
}
