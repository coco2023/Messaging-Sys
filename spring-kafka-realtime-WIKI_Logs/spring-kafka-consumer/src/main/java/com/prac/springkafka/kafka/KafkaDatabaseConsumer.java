package com.prac.springkafka.kafka;

import com.prac.springkafka.entity.WikimediaData;
import com.prac.springkafka.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.scanner.Constant;

@Service
public class KafkaDatabaseConsumer {
    private static  final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    private WikimediaDataRepository dataRepository;

    public KafkaDatabaseConsumer(WikimediaDataRepository dataRepository){
        this.dataRepository = dataRepository;
    }

    @KafkaListener(
            topics = "wikimedia_recent_change",
            groupId = "myGroup"
    )
    public void consume(String eventMessage){
        LOGGER.info(String.format("Event message received -> %s", eventMessage));

        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
        LOGGER.info("wikimediaData -> %s", wikimediaData);
        dataRepository.save(wikimediaData);
    }

}