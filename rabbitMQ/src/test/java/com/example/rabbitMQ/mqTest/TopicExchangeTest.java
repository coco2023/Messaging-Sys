package com.example.rabbitMQ.mqTest;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TopicExchangeTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private TopicExchange topicExchange;

    @Test
    public void testTopicExchange() {
        String routingKey = "stock.tech.apple";
        String message = "Test Topic Exchange";
        rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, message);

        int messageCount = rabbitAdmin.getQueueInfo("TechStocksQueue").getMessageCount();
        assertEquals(1, messageCount);
    }
}
