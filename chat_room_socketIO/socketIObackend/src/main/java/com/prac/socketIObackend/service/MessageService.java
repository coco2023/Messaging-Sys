package com.prac.socketIObackend.service;

import com.prac.socketIObackend.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessageByRoom(String room);

    Message saveMessage(Message message);
}
