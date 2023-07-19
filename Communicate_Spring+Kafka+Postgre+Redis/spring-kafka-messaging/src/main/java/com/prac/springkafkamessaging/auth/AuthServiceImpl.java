package com.prac.springkafkamessaging.auth;

import com.prac.springkafkamessaging.cache.CacheRepository;
import com.prac.springkafkamessaging.persistent.model.AccessToken;
import com.prac.springkafkamessaging.persistent.repository.AccessTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    CacheRepository cacheRepository;

    @Autowired
    AccessTokenRepository accessTokenRepository;


    @Override
    public void putAccessToken(String token, Long userId) {
        // store token in the cache
        cacheRepository.putAccessToken(token, String.valueOf(userId));

        // store token in the persistence
        AccessToken accessToken = AccessToken.builder()
                .token(token)
                .userId(userId)
                .createdAt(Calendar.getInstance().getTime())
                .build();
        accessTokenRepository.save(accessToken);

    }

    @Override
    public void loginWithAccessToken(String mobile, String code) {
        // TODO
    }
}
