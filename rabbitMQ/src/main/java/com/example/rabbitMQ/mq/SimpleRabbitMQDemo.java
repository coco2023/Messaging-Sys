package com.example.rabbitMQ.mq;

public class SimpleRabbitMQDemo {
    public static void main(String[] args) throws InterruptedException {
        Exchange exchange = new Exchange();
        MessageQueue orderQueue = new MessageQueue();
        DeadLetterQueue dlxQueue = new DeadLetterQueue();

        exchange.bindQueue("order", orderQueue);
        exchange.bindQueue("order_dlx", dlxQueue);

        Sender sender = new Sender(exchange);

        new Thread(new Listener(orderQueue)).start(); // 启动订单处理监听器
        new Thread(new Listener(dlxQueue)).start(); // 启动死信队列监听器

        sender.sendOrder("Order1");
        sender.sendOrder("Order2");

        // 模拟订单超时，直接发送到死信队列
        exchange.sendMessage("order_dlx", "Expired Order");
    }
}
