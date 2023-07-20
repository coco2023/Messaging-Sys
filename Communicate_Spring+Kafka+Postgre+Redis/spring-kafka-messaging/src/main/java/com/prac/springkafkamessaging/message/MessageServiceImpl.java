package com.prac.springkafkamessaging.message;

import com.prac.springkafkamessaging.cache.CacheRepository;
import com.prac.springkafkamessaging.persistent.model.Message;
import com.prac.springkafkamessaging.persistent.model.User;
import com.prac.springkafkamessaging.persistent.repository.MessageRepository;
import com.prac.springkafkamessaging.persistent.repository.UserRepository;
import com.prac.springkafkamessaging.websocket.MessageHandler;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void sendMessage(String accessToken, Long sendTo, String msg) {

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
            return;
        }

        try {
            // enrich message with senderId
            JSONObject msgJson = new JSONObject();
            msgJson.put("msg", msg);
            msgJson.put("senderId", senderUserId);
            messageHandler.sendMessageToUser(sendTo, msgJson.toString());
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public List<Message> getMessageHistory(Long fromUserId, Long toUserId) {
        return messageRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);
    }

    private void storeMessageToUser(Message message){
        messageRepository.save(message);
    }
}
