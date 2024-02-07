package com.example.rabbitMQ.mq2;

public class OrderProcessorListener implements Runnable {
    private MessageQueue queue;
    private String message;

    public OrderProcessorListener(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                message = queue.consume();
                // 模拟订单处理逻辑
                System.out.println("Processing order: " + message);
                // 假设有一定概率订单处理失败
                if (Math.random() > 0.7) {
                    throw new RuntimeException("Failed to process order: " + message);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            // 将失败的订单发送到死信队列
            DeadLetterQueue dlq = new DeadLetterQueue();
            try {
                dlq.produce(message);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}