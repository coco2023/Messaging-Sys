package com.prac.springkafkamessaging.auth;

import com.prac.springkafkamessaging.cache.CacheRepository;
import com.prac.springkafkamessaging.persistent.model.AccessToken;
import com.prac.springkafkamessaging.persistent.model.User;
import com.prac.springkafkamessaging.persistent.repository.AccessTokenRepository;
import com.prac.springkafkamessaging.persistent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private UserRepository userRepository;


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
    public Long loginWithAccessToken(String token) {
        String userIdStr = cacheRepository.getUserIdByAccessToken(token);
        if (userIdStr == null) {
            User user = userRepository.findByToken(token);
            if (user != null) {
                return user.getUserId();
            } else {
                return 0L;
            }
        }
        return Long.valueOf(userIdStr);
    }

    @Override
    public AccessToken getAccessToken(Long userId) {
        return accessTokenRepository.findByUserId(userId);
    }
}
