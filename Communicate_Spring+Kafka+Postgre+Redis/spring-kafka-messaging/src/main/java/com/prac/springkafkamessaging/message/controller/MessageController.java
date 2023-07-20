package com.prac.springkafkamessaging.message.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.springkafkamessaging.message.broker.MessageSender;
import com.prac.springkafkamessaging.message.dto.request.SendMessageRequest;
import com.prac.springkafkamessaging.message.dto.response.SendMessageResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
@Log4j2
public class MessageController {

    @Autowired
    private MessageSender messageSender;

    private String topic = "WEATHER";


    @RequestMapping(value = "/send-message", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendMessage (@RequestBody SendMessageRequest sendMessageRequest) {

        // invoke MessageService to send the message to the selected user(contact)

        ObjectMapper mapper = new ObjectMapper();

        try {
            // send the message
            messageSender.send(topic,
                    mapper.writeValueAsString(sendMessageRequest));

//            SendMessageResponse sendMessageResponse = SendMessageResponse.builder()
//                    .message("Message sent successfully")
//                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(
                "Message sent to Kafka topic",
                HttpStatus.OK
        );
    }
}
