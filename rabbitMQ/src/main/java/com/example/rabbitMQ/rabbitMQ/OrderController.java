package com.example.rabbitMQ.rabbitMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final RabbitMQSender rabbitMQSender;

    @Autowired
    public OrderController(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendOrder(@RequestParam String orderId) {
        rabbitMQSender.sendOrder(orderId);
        return ResponseEntity.ok("Order sent");
    }
}
