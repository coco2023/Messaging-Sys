package com.example.rabbitMQ.mq2;

public class Listener implements Runnable {
    private MessageQueue queue;

    public Listener(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = queue.consume();
                // 处理消息的逻辑
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
