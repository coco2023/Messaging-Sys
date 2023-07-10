# refer
[1] Publish/Subscribe: https://www.rabbitmq.com/tutorials/tutorial-three-spring-amqp.html

[2] Github: https://github.com/rabbitmq/rabbitmq-tutorials/blob/main/spring-amqp/src/main/java/org/springframework/amqp/tutorials/tut3/Tut3Sender.java

# small change
```	
<groupId>com.prac</groupId>
<artifactId>rabbitMQ</artifactId>
<version>0.0.2-SNAPSHOT</version>
<name>rabbitMQ</name>
<description>Demo project for Spring Boot</description>
```

# running
1. ./mvn clean package
2. run RabbitMQ via docker: docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
 3. `java -jar target/rabbitMQ-0.0.2-SNAPSHOT.jar --spring.profiles.active=pub-sub,receiver --tutorial.client.duration=60000`

4. `java -jar target/rabbitMQ-0.0.2-SNAPSHOT.jar --spring.profiles.active=pub-sub,sender --tutorial.client.duration=60000`
