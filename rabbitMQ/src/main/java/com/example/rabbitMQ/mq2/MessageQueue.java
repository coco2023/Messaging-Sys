package com.example.rabbitMQ.mq2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public void produce(String message) throws InterruptedException {
        queue.put(message);
        System.out.println("Produced: " + message);
    }

    public String consume() throws InterruptedException {
        String message = queue.take();
        System.out.println("Consumed: " + message);
        return message;
    }
}
