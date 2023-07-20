package com.prac.springkafkamessaging.auth;

import com.prac.springkafkamessaging.persistent.model.AccessToken;

public interface AuthService {

    void putAccessToken(String code, Long userId);

    Long loginWithAccessToken(String token);

    AccessToken getAccessToken(Long userId);
}
