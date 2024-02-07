package com.example.rabbitMQ.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 正常的订单队列和交换机
    public static final String ORDER_QUEUE = "order.queue";
    public static final String ORDER_EXCHANGE = "order.exchange";

    // 死信队列和交换机
    public static final String DLX_ORDER_QUEUE = "dlx.order.queue";
    public static final String DLX_ORDER_EXCHANGE = "dlx.order.exchange";

    // 正常订单队列
    @Bean
    Queue orderQueue() {
        return QueueBuilder.durable(ORDER_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_ORDER_EXCHANGE) // 设置死信交换机
                .withArgument("x-dead-letter-routing-key", "dlx.order") // 设置死信路由键
                .ttl(3000)
                .build();
    }

    // 正常订单交换机
    @Bean
    DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    // 绑定正常订单队列和交换机
    @Bean
    Binding orderBinding(Queue orderQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order");
    }

    // 死信队列
    @Bean
    Queue dlxOrderQueue() {
        return QueueBuilder.durable(DLX_ORDER_QUEUE).build();
    }

    // 死信交换机
    @Bean
    DirectExchange dlxOrderExchange() {
        return new DirectExchange(DLX_ORDER_EXCHANGE);
    }

    // 绑定死信队列和交换机
    @Bean
    Binding dlxOrderBinding(Queue dlxOrderQueue, DirectExchange dlxOrderExchange) {
        return BindingBuilder.bind(dlxOrderQueue).to(dlxOrderExchange).with("dlx.order");
    }
}
