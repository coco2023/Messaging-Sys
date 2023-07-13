
## refer
[1] https://www.rabbitmq.com/tutorials/tutorial-six-spring-amqp.html

[2] Github: https://github.com/rabbitmq/rabbitmq-tutorials/blob/main/spring-amqp/README.md

## running
1. `java -jar target/rabbitMQ-0.0.1-SNAPSHOT.jar --spring.profiles.active=rpc,server --tutorial.client.duration=60000`

2. `java -jar target/rabbitMQ-0.0.1-SNAPSHOT.jar --spring.profiles.active=rpc,client`
