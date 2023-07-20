## Refer

[1] dependencies: https://www.endpointdev.com/blog/2020/04/messaging-app-spring-kafka-pt-one/

[2] https://www.endpointdev.com/blog/2020/04/messaging-app-spring-kafka-pt-two/

[3] https://www.endpointdev.com/blog/2020/05/messaging-app-spring-kafka-pt-three/

[4] https://www.endpointdev.com/blog/2021/01/messaging-app-spring-kafka-pt-four/

[5] https://www.endpointdev.com/blog/2022/10/messaging-app-spring-kafka-pt-five/


## Step 2

- Kafka & Docker & Zookeeper
  1. create `docker-compose.yml`
  2. run: `docker-compose up -d`
  3. run: `docker exec -it <kafka container id> sh`  goto: `cd opt/kafka_2.13-2.8.1/bin`
  4. create topic: `kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic test-topic0719`
  5. list topic: `kafka-topics.sh --list --bootstrap-server localhost:9092`
  6. running producer: `kafka-console-producer.sh --topic test-topic0719 --bootstrap-server localhost:9092`  sending message
  7. open a new console, **repeate step 3** and run consumer: `kafka-console-consumer.sh --topic test-topic0719 --from-beginning --bootstrap-server localhost:9092` the messages from producer will be received.

- Consumer Configuration in Spring Project 
  1. using `@KafkaListener` to define `MyKafkaConsumer` in Spring project.
  2. define `MyKafkaProducer`
  3. add `application.properties`

- Postgre Config

- Redis Config
  1. start Redis: `sudo service redis-server start` enter password
  2. `redis-cli`

## Step 3

create basic enetities & their impl

1. We’re not going to integrate the cache environment as Spring persistent data, so we won’t be using the CrudRepository implementation for the cache repository
2. Activation and Authentication
3. Activation is a one-time process to activate a mobile number for our messaging service client. After an activation our simple authentication service will provide an access token to messaging client, and this access token will be used for future client logins. To achieve these simple processes we need to create our authentication service interface.

## Step 4

configure and prepare the WebSocket session pool

- authentication and storing messages on the time of socket messages and sessions received
- create a WebSocketHandler for WebSocket configuration
- WebSocket session message is received we’re going to send the message to a Kafka topic
- define our WebSocket message handler `MessageHandler` and Kafka message producer `MessageSender`. We also need a session pool `WebSocketPool` so we can manage the client sessions

file changes: <br>
<img src="doc/step4%20-%20file%20changes.png"  width="50%" height="20%">


## Step 5

- ERROR <br>
`Can't run Jedis example: JedisConnectionException: Could not get a resource from the pool` <br>
Solution: testing by `jedisPool.getResource()`
- Then Got ERROR: `ERR AUTH <password> called without any password configured for the default user. Are you sure your configuration is corre` <br>
  - Solution: https://medium.com/@umutuluer/resolving-the-err-client-sent-auth-but-no-password-is-set-error-b81438d10843
  ```
  > redis-cli
  > CONFIG SET requirepass "12345"
  > AUTH 12345
  ```

- ERROR: ` servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.dao.InvalidDataAccessResourceUsageException: JDBC exception executing SQL [select u1_0.user_id,u1_0.created_at,u1_0.fname,u1_0.lname,u1_0.mobile from users u1_0 where u1_0.mobile=?] [ERROR: relation "users" does not exist Position: 76] [n/a]; SQL [n/a]] with root cause` <br>
  - Solution: `spring.jpa.hibernate.ddl-auto=update`

## Postgre Database

1. Postgre database <br>
![postgre](doc/postgre.png)

2. Kafka Producer-Consumer Messaging sending <br>
![messages](doc/step2.png)
   
3. getcode - step5
![5-getcode](doc/5-getcode.png)

4. login - step5
![](doc/5-login.png)