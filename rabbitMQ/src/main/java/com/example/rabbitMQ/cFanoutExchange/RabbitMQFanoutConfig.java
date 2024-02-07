package com.example.rabbitMQ.cFanoutExchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQFanoutConfig {

    @Bean
    Queue emailQueue() {
        return new Queue("EmailNotificationQueue", false);
    }

    @Bean
    Queue smsQueue() {
        return new Queue("SMSNotificationQueue", false);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("NotificationsExchange");
    }

    @Bean
    Binding emailBinding(Queue emailQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(emailQueue).to(fanoutExchange);
    }

    @Bean
    Binding smsBinding(Queue smsQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(smsQueue).to(fanoutExchange);
    }
}
