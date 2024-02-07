package com.example.rabbitMQ.cFanoutExchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @RabbitListener(queues = "EmailNotificationQueue")
    public void receiveEmail(String message) {
        System.out.println("Received email notification: " + message);
        // Process email notification
    }

    @RabbitListener(queues = "SMSNotificationQueue")
    public void receiveSMS(String message) {
        System.out.println("Received SMS notification: " + message);
        // Process SMS notification
    }
}
