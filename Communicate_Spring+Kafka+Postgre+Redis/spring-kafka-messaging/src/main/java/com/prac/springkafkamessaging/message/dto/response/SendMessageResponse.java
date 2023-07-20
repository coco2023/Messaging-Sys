package com.prac.springkafkamessaging.message.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class SendMessageResponse {

    private String message;

}
