package com.example.rabbitMQ.bTopicExchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockMessageListener {

    @RabbitListener(queues = "TechStocksQueue")
    public void receiveTechStockMessage(String message) {
        System.out.println("Received tech stock message: " + message);
        // Process tech stock update
    }

    @RabbitListener(queues = "FinanceStocksQueue")
    public void receiveFinanceStockMessage(String message) {
        System.out.println("Received finance stock message: " + message);
        // Process finance stock update
    }
}
