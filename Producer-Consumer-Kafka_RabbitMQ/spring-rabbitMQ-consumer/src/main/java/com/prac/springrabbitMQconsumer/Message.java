package com.prac.springrabbitMQconsumer;

import lombok.*;

import java.util.Date;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Message {
    private String messageId;
    private String message;
    private Date messageDate;
}