package com.prac.springkafkamessaging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContactRequest {

    private String accessToken;

    private Long addUserId;

    private String mobile;

}
