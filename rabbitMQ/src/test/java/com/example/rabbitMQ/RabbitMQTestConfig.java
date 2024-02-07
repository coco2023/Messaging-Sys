//package com.example.rabbitMQ;
//
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//
//@TestConfiguration
//public class RabbitMQTestConfig {
//
//    @Bean
//    @Primary
//    public RabbitTemplate testRabbitTemplate(RabbitTemplate originalRabbitTemplate) {
//        // 通过覆盖RabbitTemplate来自定义一些测试行为，如果需要的话
//        return originalRabbitTemplate;
//    }
//}
