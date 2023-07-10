# refer: 
[1] Work Queues: https://www.rabbitmq.com/tutorials/tutorial-two-spring-amqp.html

[2] Messaging Patterns: https://www.enterpriseintegrationpatterns.com/patterns/messaging/CompetingConsumers.html

# running
1. `docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management`

2. `./mvnw clean package`

3. `java -jar target/rabbitMQ-0.0.1-SNAPSHOT.jar --spring.profiles.active=work-queues,receiver`

4. `java -jar target/rabbitMQ-0.0.1-SNAPSHOT.jar --spring.profiles.active=work-queues,sender`