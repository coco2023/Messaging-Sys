package com.example.rabbitMQ.bTopicExchange;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockUpdateService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange stockUpdatesExchange;

    public void sendStockUpdate(String routingKey, String message) {
        rabbitTemplate.convertAndSend(stockUpdatesExchange.getName(), routingKey, message);
    }
}
