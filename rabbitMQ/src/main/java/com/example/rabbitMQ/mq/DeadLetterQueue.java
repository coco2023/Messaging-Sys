package com.example.rabbitMQ.mq;

public class DeadLetterQueue extends MessageQueue {
    // 重试次数
    private int retryCount = 3;

    public void retry(String message) throws InterruptedException {
        System.out.println("Retrying message: " + message);
        // 简化示例：直接尝试重新处理消息
        for (int i = 0; i < retryCount; i++) {
            System.out.println("Retry " + (i + 1) + " for message: " + message);
            // 模拟处理逻辑
            Thread.sleep(1000); // 假设处理需要一定时间
            if (Math.random() > 0.5) { // 假设有一定概率处理成功
                System.out.println("Message processed on retry " + (i + 1) + ": " + message);
                return;
            }
        }
        System.out.println("Failed to process message after retries: " + message);
        // 可以在这里添加进一步的处理逻辑，比如记录日志、警告通知等
    }
}
