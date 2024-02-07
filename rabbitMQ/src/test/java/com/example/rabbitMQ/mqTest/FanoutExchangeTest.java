package com.example.rabbitMQ.mqTest;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FanoutExchangeTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private FanoutExchange fanoutExchange;

    @Test
    public void testFanoutExchange() {
        String message = "Test Fanout Exchange";
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", message);

        int emailQueueCount = rabbitAdmin.getQueueInfo("EmailNotificationQueue").getMessageCount();
        int smsQueueCount = rabbitAdmin.getQueueInfo("SMSNotificationQueue").getMessageCount();

        assertTrue(emailQueueCount > 0 && smsQueueCount > 0);
    }
}
