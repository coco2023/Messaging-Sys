package com.example.rabbitMQ.dHeadersExchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class IoTDataListener {

    @RabbitListener(queues = "TemperatureSensorsQueue")
    public void receiveTemperatureSensorData(String message) {
        System.out.println("Received temperature sensor data: " + message);
        // Process temperature sensor data
    }
}
