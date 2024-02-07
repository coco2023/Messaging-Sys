package com.example.rabbitMQ.bTopicExchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopicConfig {

    @Bean
    public TopicExchange stockUpdatesExchange() {
        return new TopicExchange("StockUpdates");
    }

    @Bean
    public Queue techStocksQueue() {
        return new Queue("TechStocksQueue");
    }

    @Bean
    public Queue financeStocksQueue() {
        return new Queue("FinanceStocksQueue");
    }

    @Bean
    public Binding techStocksBinding(Queue techStocksQueue, TopicExchange stockUpdatesExchange) {
        return BindingBuilder.bind(techStocksQueue).to(stockUpdatesExchange).with("stock.tech.*");
    }

    @Bean
    public Binding financeStocksBinding(Queue financeStocksQueue, TopicExchange stockUpdatesExchange) {
        return BindingBuilder.bind(financeStocksQueue).to(stockUpdatesExchange).with("stock.finance.*");
    }
}
