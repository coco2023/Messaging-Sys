package com.prac.springkafkamessaging.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class MyKafkaConsumer {

    @KafkaListener(topics = "test-topic0719")
    public void listenTopic(ConsumerRecord<String, String> kafkaMessage) {
        System.out.println(String.format("Received a message: %s", kafkaMessage.value()));
    }
}
