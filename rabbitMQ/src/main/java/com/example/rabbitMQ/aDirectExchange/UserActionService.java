package com.example.rabbitMQ.aDirectExchange;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActionService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange exchange;

    public void sendLoginAction(String message) {
        rabbitTemplate.convertAndSend(exchange.getName(), "login", message);
    }

    public void sendSignupAction(String message) {
        rabbitTemplate.convertAndSend(exchange.getName(), "signup", message);
    }
}
