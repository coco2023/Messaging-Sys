package com.prac.springkafkamessaging.service.message;

import com.prac.springkafkamessaging.entity.Message;

import java.util.List;

/**
 * define common messaging methods to be utilized by the Kafka listeners
 *
 */
public interface MessageService {

    /**
     * @param accessToken: will be used to authenticate user sending the message
     * @param sendTo: the user_id of the recipient
     * @param msg: message
     */
    public void sendMessage(String accessToken, Long sendTo, String msg);

    List<Message> getMessageHistory(Long fromUserId, Long toUserId);

    void sendMessageToUser(String accessToken, Long sendTo, String msg, String topic);
}
