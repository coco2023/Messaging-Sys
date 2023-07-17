package com.prac.socketIObackend.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.prac.socketIObackend.constant.MesgPrefix;
import com.prac.socketIObackend.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Slf4j

public class SocketModel {

    @Autowired
    private SocketIOServer server;

    @Autowired
    private SocketService socketService;

    public SocketModel(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        // "send_message" will connect to front "send_message" socket.emit
        server.addEventListener("send_message", Message.class, onChatReceived());
    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            socketService.saveClientMessage(senderClient, data);
        };
    }

    private DisconnectListener onDisconnected() {
        return (client) -> {
            var params = client.getHandshakeData().getUrlParams();
            String room = params.get("room").stream().collect(Collectors.joining());
            String username = params.get("username").stream().collect(Collectors.joining());
            client.joinRoom(room);
            socketService.saveServerMessage(client, String.format(MesgPrefix.DISCONNECT_MESSAGE, username), room);
            log.info("socketID [{}] : room [{}] : username [{}]",client.getSessionId().toString(), room, username);
        };
    }

    private ConnectListener onConnected() {

        return (client) -> {
            var params = client.getHandshakeData().getUrlParams();
            String room = params.get("room").stream().collect(Collectors.joining());
            String username = params.get("username").stream().collect(Collectors.joining());
            client.joinRoom(room);
            socketService.saveServerMessage(client, String.format(MesgPrefix.WELCOME_MESSAGE, username), room);
            log.info("socketID [{}] : room [{}] : username [{}]",client.getSessionId().toString(), room, username);
        };

    }

}
