package com.prac.springkafkamessaging.persistent.repository;

import com.prac.springkafkamessaging.persistent.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
