package com.example.rabbitMQ.mq;

import java.util.HashMap;
import java.util.Map;

public class Exchange {
    private Map<String, MessageQueue> bindings = new HashMap<>();

    public void bindQueue(String routingKey, MessageQueue queue) {
        bindings.put(routingKey, queue);
    }

    public void sendMessage(String routingKey, String message) throws InterruptedException {
        if (bindings.containsKey(routingKey)) {
            MessageQueue queue = bindings.get(routingKey);
            queue.produce(message);
        } else {
            System.out.println("No queue bound for routing key: " + routingKey);
        }
    }
}
