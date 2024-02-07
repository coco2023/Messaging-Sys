package com.prac.springkafkamessaging.kafka;

import com.prac.springkafkamessaging.service.message.MessageService;
import com.prac.springkafkamessaging.websocket.WebSocketPool;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver {

    @Autowired
    private MessageService messageService;

    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    private final static String topic = "WEATHER";

    @KafkaListener(topics = topic)
    public void messagesSendToUser(@Payload String message, @Headers MessageHeaders headers) {

        // convert String into JSON object
        JSONObject jsonObject = new JSONObject(message);
        LOG.info("***jsonObject:" + jsonObject.keys() + ", headers:" + headers);

        // TODO: add contacts to users

        if (jsonObject.get("sendTo") != null &&
                WebSocketPool.websockets.get(jsonObject.getLong("sendTo")) != null &&
                WebSocketPool.websockets.get(jsonObject.getLong("sendTo")).size() > 0
        ) {

            String accessToken = jsonObject.getString("accessToken");
            Long sendTo = jsonObject.getLong("sendTo");
            String msg = jsonObject.getString("msg");

            LOG.info("Websocket message is sent to " + sendTo);

            messageService.sendMessageToUser(accessToken, sendTo, msg, topic);

        } else {
            LOG.info("Websocket session not found for given sendTo");
        }

//        if (WebSocketPool.websockets.get(jsonObject.getString("sendTo")) != null) {
//            String accessToken = jsonObject.getString("accessToken");
//            Long sendTo = Long.parseLong(jsonObject.getString("sendTo"));
//            String msg = jsonObject.getString("msg");
//
//            messageService.sendMessage(accessToken, sendTo, msg);
//        }
    }

}
