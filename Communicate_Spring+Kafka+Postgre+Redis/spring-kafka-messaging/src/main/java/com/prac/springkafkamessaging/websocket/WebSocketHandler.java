package com.prac.springkafkamessaging.websocket;

import org.json.JSONObject;

import com.prac.springkafkamessaging.cache.CacheRepository;
import com.prac.springkafkamessaging.persistent.model.User;
import com.prac.springkafkamessaging.persistent.repository.UserRepository;
import com.prac.springkafkamessaging.message.broker.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * WebSocketHandler:
 * utilize MessageHandler to add and remove user sessions and MessageSender to send WebSocketSession messages to Kafka topics
 *
 * topics:
 * send messages (SEND_MESSAGE), or
 * new contact requests, notifications
 */

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private String currentTopic = "WEATHER";

    @Autowired
    CacheRepository cacheRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageHandler messageHandler;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String parameters[] = session.getUri().getQuery().split("=");

        if (parameters.length == 2 && parameters[0].equals("accessToken")) {

            String accessToken = parameters[1];

            Long senderUserId = 0L;
            String senderId = cacheRepository.getUserIdByAccessToken(accessToken);

            if (senderId == null) {
                User sender = userRepository.findByToken(accessToken);
                if (sender != null) {
                    senderUserId = sender.getUserId();
                }
                else {
                    senderUserId = Long.valueOf(senderId);
                }
                if (senderUserId == 0L) {
                    return;
                }
            }

            messageHandler.removeFromSessionToPool(senderUserId, session);

        }

    }


    @Override
    public void afterConnectionEstablished (WebSocketSession session) throws  Exception {

        String parameters[] = session.getUri().getQuery().split("=");

        if (parameters.length == 2 && parameters[0].equals("accessToken")) {
            String accessToken = parameters[1];

            Long senderUserId = 0L;
            String senderId = cacheRepository.getUserIdByAccessToken(accessToken);

            if (senderId == null) {
                User sender = userRepository.findByToken(accessToken);
                if (sender != null) {
                    senderUserId = sender.getUserId();
                }
            } else {
                senderUserId = Long.valueOf(senderId);
            }
            if (senderUserId == 0L) {
                return;
            }

            messageHandler.addSessionToPool(senderUserId, session);
        } else {
            session.close();
        }
    }

    @Autowired
    private MessageSender messageSender;

    @Override
    protected void handleTextMessage (WebSocketSession session, TextMessage textMessage) throws Exception {

        JSONObject jsonObject = new JSONObject(textMessage.getPayload());
        String topic = jsonObject.getString("topic");
        JSONObject message = jsonObject.getJSONObject("message");

        // only SEND_MESSAGE topic is available
        if (topic == null && !topic.equals(currentTopic)) {
            return;
        }

        messageSender.send(topic, message.toString());
    }
}
