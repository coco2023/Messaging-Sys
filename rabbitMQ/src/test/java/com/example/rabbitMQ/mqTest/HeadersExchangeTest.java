package com.example.rabbitMQ.mqTest;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HeadersExchangeTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private HeadersExchange headersExchange;

    @Test
    public void testHeadersExchange() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("device_type", "temperature_sensor");
        headers.put("location", "warehouse");

        Message<String> message = MessageBuilder.withPayload("Test Headers Exchange").copyHeaders(headers).build();
        rabbitTemplate.convertAndSend(headersExchange.getName(), "", message);

        int messageCount = rabbitAdmin.getQueueInfo("TemperatureSensorsQueue").getMessageCount();
        assertEquals(1, messageCount);
    }
}
