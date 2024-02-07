package com.example.rabbitMQ.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DlxOrderListener {

    @RabbitListener(queues = RabbitMQConfig.DLX_ORDER_QUEUE)
    public void onDlxMessage(String orderId) {
        // 处理超时订单，例如取消订单
        System.out.println("Handling expired order: " + orderId);
        // 取消订单的逻辑...
    }
}
