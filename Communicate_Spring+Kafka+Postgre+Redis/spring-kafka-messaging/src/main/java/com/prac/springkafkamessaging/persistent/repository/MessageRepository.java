package com.prac.springkafkamessaging.persistent.repository;

import com.prac.springkafkamessaging.persistent.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
