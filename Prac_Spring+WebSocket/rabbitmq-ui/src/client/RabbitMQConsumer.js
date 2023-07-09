
import React, { useEffect, useState } from 'react';
import { connect } from 'amqplib';

const RabbitMQConsumer = () => {
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    const connectToRabbitMQ = async () => {
      try {
        const connection = await connect('amqp://guest:guest@localhost:5672');
        const channel = await connection.createChannel();

        const exchange = 'amq.topic';
        const routingKey = 'messages';
        const queueName = 'my-queue';

        await channel.assertQueue(queueName, { durable: false });
        await channel.bindQueue(queueName, exchange, routingKey);

        channel.consume(queueName, (msg) => {
          if (msg !== null) {
            const message = msg.content.toString();
            console.log('Received:', message);
            setMessages((prevMessages) => [...prevMessages, message]);
            channel.ack(msg);
          }
        });
      } catch (error) {
        console.error('Error connecting to RabbitMQ:', error);
      }
    };

    connectToRabbitMQ();

  }, []);

  return (
    <div>
      <h2>Received Messages:</h2>
      <ul>
        {messages.map((message, index) => (
          <li key={index}>{message}</li>
        ))}
      </ul>
    </div>
  );
};

export default RabbitMQConsumer;
