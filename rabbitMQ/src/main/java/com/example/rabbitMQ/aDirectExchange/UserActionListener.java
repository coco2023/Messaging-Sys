package com.example.rabbitMQ.aDirectExchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserActionListener {

    @RabbitListener(queues = "LoginQueue")
    public void receiveLoginMessage(String message) {
        System.out.println("Received login message: " + message);
        // Process login action
    }

    @RabbitListener(queues = "SignupQueue")
    public void receiveSignupMessage(String message) {
        System.out.println("Received signup message: " + message);
        // Process signup action
    }
}
