package com.prac.springkafkamessaging.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class MyKafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        System.out.println(String.format("Message is being sent to topic %s", topic));
        kafkaTemplate.send(topic, message);
    }
}
