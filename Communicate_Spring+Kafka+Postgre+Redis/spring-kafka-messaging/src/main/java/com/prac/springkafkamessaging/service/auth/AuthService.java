package com.prac.springkafkamessaging.service.auth;

import com.prac.springkafkamessaging.entity.AccessToken;

public interface AuthService {

    void putAccessToken(String code, Long userId);

    Long loginWithAccessToken(String token);

    AccessToken getAccessToken(Long userId);
}
