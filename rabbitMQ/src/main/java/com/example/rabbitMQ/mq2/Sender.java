package com.example.rabbitMQ.mq2;

public class Sender {
    private Exchange exchange;

    public Sender(Exchange exchange) {
        this.exchange = exchange;
    }

    public void sendOrder(String order) throws InterruptedException {
        exchange.sendMessage("order", order); // 假设所有订单都使用"order"作为路由键
    }
}
