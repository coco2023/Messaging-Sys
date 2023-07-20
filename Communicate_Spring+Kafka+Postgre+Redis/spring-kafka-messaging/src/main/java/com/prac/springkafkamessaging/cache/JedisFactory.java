package com.prac.springkafkamessaging.cache;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Log4j2
public class JedisFactory {

//    @Value("${cache.redis.host}")
    @Value("${spring.data.redis.host}")
    private static String host="localhost";

//    @Value("${cache.redis.port}")
    @Value("${spring.data.redis.port}")
    private static Integer port = 6379;

//    @Value("${cache.redis.timeout}")
    private static Integer timeout = 10000;

//    @Value("${cache.redis.password}")
    @Value("${spring.data.redis.password}")
    private static String password = "12345";

    // hide the constructor
    private JedisFactory() {

    }

    private static JedisPool jedisPool;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);

        jedisPool = new JedisPool(
                poolConfig,
                host,
                port,
                timeout,
                password
        );
        log.info("jedisPool: " + jedisPool.getResource());
    }

    public static Jedis getConnection() {
        return jedisPool.getResource();
    }
}
