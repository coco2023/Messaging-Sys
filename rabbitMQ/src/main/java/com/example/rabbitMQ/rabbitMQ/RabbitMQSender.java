package com.example.rabbitMQ.rabbitMQ;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(String orderId) {
        Message message = MessageBuilder
                .withBody(orderId.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setExpiration(String.valueOf(3000)) // 设置TTL
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, "order", message);
    }
}
