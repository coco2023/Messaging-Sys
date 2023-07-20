package com.prac.springkafkamessaging.websocket;

import com.prac.springkafkamessaging.cache.CacheRepository;
import com.prac.springkafkamessaging.persistent.model.User;
import com.prac.springkafkamessaging.persistent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class WSHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private UserRepository userRepository;

    public WSHandshakeInterceptor (CacheRepository cacheRepository, UserRepository userRepository) {
        this.cacheRepository = cacheRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        String parameters[] = request.getURI().getQuery().split("=");

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
                attributes.put("CUSTOM-HEADER", "Handshake failed: user not found for given accessToken");
                return false;
            }

            attributes.put("CUSTOM-HEADER", "Handshake successful");
            return true;
        }

        attributes.put("CUSTOM-HEADER", "Handshake failed: accessToken not found");
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // TODO
    }
}
