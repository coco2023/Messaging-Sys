# RabbitMQ

[1] rabbitMQ02: https://www.rabbitmq.com/tutorials/tutorial-one-spring-amqp.html

## run
1. build the JAR file: `./mvnw clean package`

2. run RabbitMQ via docker: `docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management` 

3. run the receiver: `java -jar target/rabbitMQ02-0.0.1-SNAPSHOT.jar --spring.profiles.active=hello-world,receiver`

4. run the sender: `java -jar target/rabbitMQ02-0.0.1-SNAPSHOT.jar --spring.profiles.active=hello-world,sender`

5. get the result:
- console output: ` [x] Sent 'Hello Han!'`
- go to : http://localhost:15672/
  - username, password: guest, guest
  - then check the results in `Querys and Streams`

6. stop rabbitMQ docker: 
    ```
    docker stop <Container_ID>
    docker rm <Container_ID>
    ```







