package com.example.rabbitMQ.dHeadersExchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQHeadersConfig {

    @Bean
    Queue temperatureSensorsQueue() {
        return new Queue("TemperatureSensorsQueue", false);
    }

    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange("IoTData");
    }

    @Bean
    Binding temperatureSensorsBinding(Queue temperatureSensorsQueue, HeadersExchange headersExchange) {
        Map<String, Object> headerValues = new HashMap<>();
        headerValues.put("device_type", "temperature_sensor");
        headerValues.put("location", "warehouse");
        // Use 'whereAll' for matching all headers or 'whereAny' for matching any of the headers
        return BindingBuilder.bind(temperatureSensorsQueue).to(headersExchange).whereAll(headerValues).match();
    }
}
