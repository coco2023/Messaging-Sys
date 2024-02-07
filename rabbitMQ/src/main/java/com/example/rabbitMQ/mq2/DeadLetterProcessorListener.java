package com.example.rabbitMQ.mq2;

public class DeadLetterProcessorListener implements Runnable {
    private DeadLetterQueue dlq;

    public DeadLetterProcessorListener(DeadLetterQueue dlq) {
        this.dlq = dlq;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = dlq.consume();
                // 使用死信队列中的重试逻辑处理消息
                dlq.retry(message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
