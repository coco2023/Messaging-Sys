
## refer
[1] https://www.rabbitmq.com/tutorials/tutorial-five-spring-amqp.html

## running
1. `./mvnw clean package`
2. `docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management`
3. consumer: `java -jar target/rabbitMQ-0.0.1-SNAPSHOT.jar --spring.profiles.active=topics,receiver --tutorial.client.duration=60000`
4. sender: `java -jar target/rabbitMQ-0.0.1-SNAPSHOT.jar --spring.profiles.active=topics,sender --tutorial.client.duration=60000`
5. result: <br>

## Topic exchange
Messages sent to a topic exchange are a list of words, delimited by dots

- `*` (star) can substitute for exactly one word.
- `#` (hash) can substitute for zero or more words.
- When a queue is bound with "#" (hash) binding key - it will receive all the messages, regardless of the routing key - like in fanout exchange.
- When special characters "*" (star) and "#" (hash) aren't used in bindings, the topic exchange will behave just like a direct one.



![](https://www.rabbitmq.com/img/tutorials/python-five.png)



