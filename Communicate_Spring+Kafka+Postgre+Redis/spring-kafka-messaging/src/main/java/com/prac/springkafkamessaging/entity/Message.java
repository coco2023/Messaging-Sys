package com.prac.springkafkamessaging.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="messages")

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="message_id")
    private String messageId;

    @Column(name="from_user_id")
    private Long fromUserId;

    @Column(name="to_user_id")
    private Long toUserId;

    @Column(name="message")
    private String message;

    @Column(name="sent_at")
    private Date sentAt;

}
