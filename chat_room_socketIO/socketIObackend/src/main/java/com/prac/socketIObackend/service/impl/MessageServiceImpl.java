package com.prac.socketIObackend.service.impl;

import com.prac.socketIObackend.entity.Message;
import com.prac.socketIObackend.repository.MessageRepository;
import com.prac.socketIObackend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> getMessageByRoom(String room) {
        return messageRepository.findAllByRoom(room);
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
