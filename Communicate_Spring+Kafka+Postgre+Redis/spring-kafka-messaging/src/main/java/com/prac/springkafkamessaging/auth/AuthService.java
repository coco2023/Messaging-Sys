package com.prac.springkafkamessaging.auth;

public interface AuthService {

    void putAccessToken(String code, Long userId);

    void loginWithAccessToken(String mobile, String code);

}
