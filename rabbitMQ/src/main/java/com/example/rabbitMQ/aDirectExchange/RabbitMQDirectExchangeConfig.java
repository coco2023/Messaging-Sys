package com.example.rabbitMQ.aDirectExchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQDirectExchangeConfig {

    @Bean
    Queue loginQueue() {
        return new Queue("LoginQueue", false);
    }

    @Bean
    Queue signupQueue() {
        return new Queue("SignupQueue", false);
    }

    @Bean
     DirectExchange exchange() {
        return new DirectExchange("UserActions");
    }

    @Bean
    Binding bindingLogin(Queue loginQueue, DirectExchange exchange) {
        return BindingBuilder.bind(loginQueue).to(exchange).with("login");
    }

    @Bean
    Binding bindingSignup(Queue signupQueue, DirectExchange exchange) {
        return BindingBuilder.bind(signupQueue).to(exchange).with("signup");
    }

}
