package com.prac.springrabbitMQconsumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component

public class MessageListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void listener(Message message) {
        System.out.println("listen from producer: " + message);
    }
}
