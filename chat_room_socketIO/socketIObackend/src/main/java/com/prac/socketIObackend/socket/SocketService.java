package com.prac.socketIObackend.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.prac.socketIObackend.constant.MessageType;
import com.prac.socketIObackend.entity.Message;
import com.prac.socketIObackend.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class SocketService {

    @Autowired
    private MessageService messageService;

    public void saveServerMessage(SocketIOClient senderClient, String message, String room) {
        Message storedMesg = messageService.saveMessage(
                Message.builder()
                        .messageType(MessageType.SERVER)
                        .content(message)
                        .room(room)
                        .build()
        );
        sendSocketMessage(senderClient, storedMesg, room);
    }

    public void saveClientMessage(SocketIOClient senderClient, Message message) {
        Message storedMesg = messageService.saveMessage(
                Message.builder()
                        .messageType(MessageType.CLIENT)
                        .content(message.getContent())
                        .room(message.getRoom())
                        .username(message.getUsername())
                        .build()
        );
        sendSocketMessage(senderClient, storedMesg, message.getRoom());
    }

    public void sendSocketMessage(SocketIOClient senderClient,
                                  Message message,
                                  String room){
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent("read_message", message);
            }
        }
    }

}
