package com.prac.springkafkamessaging.service.message;

import com.prac.springkafkamessaging.kafka.MessageReceiver;
import com.prac.springkafkamessaging.websocket.WebSocketPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * MessageHandlerImpl can:
 * adding or removing WebSocketSession sessions from the pool;
 * sending messages to a target user’s WebSocket sessions.
 */
@Service
public class MessageHandlerImpl implements MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    @Override
    public void addSessionToPool(Long userId, WebSocketSession session) {

        Set<WebSocketSession> userSessions = WebSocketPool.websockets.get(userId);

        if (userSessions != null) {
            userSessions.add(session);
            WebSocketPool.websockets.put(userId, userSessions);
        } else {
            Set<WebSocketSession> newUserSessions = new HashSet<>();
            newUserSessions.add(session);
            WebSocketPool.websockets.put(userId, newUserSessions);
        }

    }

    @Override
    public void sendMessageToUser(Long userId, String message) throws IOException {

        Set<WebSocketSession> userSessions = WebSocketPool.websockets.get(userId);

        if (userSessions == null) {
            return;
        }

        TextMessage textMessage = new TextMessage(message);
        for (WebSocketSession session : userSessions) {
            session.sendMessage(textMessage);
        }

    }

    @Override
    public void removeFromSessionToPool(Long userId, WebSocketSession session) {

        Set<WebSocketSession> userSessions = WebSocketPool.websockets.get(userId);

        if (userSessions != null) {
            for (WebSocketSession sessionItem : userSessions) {
                if (sessionItem.equals(session)) {
                    userSessions.remove(session);
                } else {
                    LOG.info("This session is not equal hmm: " + sessionItem.hashCode() + " <> " + session.hashCode());
                }
            }
        }
        WebSocketPool.websockets.put(userId, userSessions);
    }
}
