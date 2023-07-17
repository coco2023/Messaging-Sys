package com.prac.socketIObackend.controller;

import com.prac.socketIObackend.constant.MessageType;
import com.prac.socketIObackend.entity.Message;
import com.prac.socketIObackend.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@Log4j2

public class MessageController {

    @Autowired
    private MessageService messageService;

    @CrossOrigin
    @GetMapping("/{room}")
    public ResponseEntity<List<Message>> getMessage(@PathVariable String room) {

        List<Message> result = messageService.getMessageByRoom(room);
        log.info("Mesg in the room: " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
