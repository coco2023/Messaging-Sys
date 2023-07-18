package com.prac.springkafka.controller;

import com.prac.springkafka.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/kafka")
@RestController
public class MessageController {
    private KafkaProducer kafkaProducer;

    public MessageController(KafkaProducer kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    // http://localhost:9001/api/v1/kafka/publish?message=hello-world
    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message send to the topic");
    }
}