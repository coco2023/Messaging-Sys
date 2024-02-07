package com.example.rabbitMQ.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageListener {

    @RabbitListener(queues = "#{@orderQueue}")
    public void onMessage(String orderId) {
        // 处理订单逻辑，例如验证订单状态等
        System.out.println("Received order: " + orderId);
    }
}
