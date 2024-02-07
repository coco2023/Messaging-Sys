package com.example.rabbitMQ.dHeadersExchange;

import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IoTMessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private HeadersExchange headersExchange;

    public void sendMessage(Map<String, Object> headers, String message) {
        rabbitTemplate.convertAndSend(headersExchange.getName(), "", message, m -> {
            headers.forEach((key, value) -> m.getMessageProperties().setHeader(key, value));
            return m;
        });
    }
}
