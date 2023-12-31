package com.prac.springkafka.kafka;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.prac.springkafka.constants.Constant;
import com.prac.springkafka.handler.WikimediaChangesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer (KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        String topic = Constant.TOPIC_NAME;

        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);
        String url = Constant.URL;
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(10);

    }

}
