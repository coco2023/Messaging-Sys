## refer:
[1] https://www.rabbitmq.com/tutorials/tutorial-four-spring-amqp.html

## running

1. `./mvn clean package`
2. `docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management`
3. consumer: `java -jar target/rabbitMQ-0.0.1-SNAPSHOT.jar --spring.profiles.active=routing,receiver --tutorial.client.duration=60000`


4. sender: `java -jar target/rabbitMQ-0.0.1-SNAPSHOT.jar --spring.profiles.active=routing,sender --tutorial.client.duration=60000`

5. result:<br>
![](./doc/routing.png)
