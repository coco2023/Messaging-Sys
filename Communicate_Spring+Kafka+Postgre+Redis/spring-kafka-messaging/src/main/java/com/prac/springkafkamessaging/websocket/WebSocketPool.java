package com.prac.springkafkamessaging.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <user_id, <set of WebSocketSession>>
 *     This map allows multiple sessions for one user, ensuring it will work from multiple client applications
 */
public class WebSocketPool {
    public static Map<Long, Set<WebSocketSession>> websockets = new ConcurrentHashMap<>();
}
