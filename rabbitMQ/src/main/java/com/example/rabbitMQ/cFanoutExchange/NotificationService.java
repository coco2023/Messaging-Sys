package com.example.rabbitMQ.cFanoutExchange;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanoutExchange;

    public void sendNotification(String message) {
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", message);
    }
}
