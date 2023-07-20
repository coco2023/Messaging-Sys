package com.prac.springkafkamessaging.auth.controller;

import com.prac.springkafkamessaging.auth.AuthService;
import com.prac.springkafkamessaging.cache.CacheRepository;
import com.prac.springkafkamessaging.persistent.model.AccessToken;
import com.prac.springkafkamessaging.persistent.model.User;
import com.prac.springkafkamessaging.persistent.repository.UserRepository;
import com.prac.springkafkamessaging.util.StringHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.UUID;

/**
 * AuthController is consumed by a client application
 * /getcode endpoint: get an activation code for a mobile number
 * /login endpoint: passing the activation code; confirms the activation code, it will return an access token to the client for future requests
 *
 * The client application will be responsible for adding the accessToken to all WebSocket session messages
 *
 * StringHelper: to generate the activation code
 * UUID: to generate an accessToken using random UUID
 */
@RestController
@RequestMapping("/api/auth")
@Log4j2

public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    CacheRepository cacheRepository;

    @RequestMapping(value = "/getcode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getCode(@Valid @RequestBody ActivationRequest activationRequest) {

        int code = StringHelper.generateRandomNumber(6);

        // save the activation code to the cache repository (cached auth token)
        cacheRepository.putActivationCode(activationRequest.getMobile(), String.valueOf(code));

        ActivationResponse activationResponse = ActivationResponse.builder()
                .mobile(activationRequest.getMobile())
                .activationCode(String.valueOf(code))
                .build();

        return new ResponseEntity<>(
                activationResponse,
                HttpStatus.OK
        );
    }

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {

        String mobile = cacheRepository.queryMobileActivationCode(loginRequest.getMobile(), loginRequest.getActivationCode());

        if (mobile == null) {
            return new ResponseEntity<>(
                    "Mobile number not found!",
                    HttpStatus.NOT_FOUND
            );
        } else {
            Long userId = 0L;

            log.info("loginRequest.getMobile():" + loginRequest.getMobile());
            User user = userRepository.findByMobile(loginRequest.getMobile());

            if(user == null) {
                // save user in persistence
                userRepository.save(
                        User.builder()
                                .mobile(loginRequest.getMobile())
                                .fname(loginRequest.getMobile())
                                .lname(loginRequest.getMobile())
                                .createdAt(Calendar.getInstance().getTime())
                                .build()
                );
                user = userRepository.findByMobile(loginRequest.getMobile());
            }

            log.info("user:" + user);
            userId = user.getUserId();
            AccessToken accessToken = authService.getAccessToken(userId);

            String token = "";
            if (accessToken == null) {
                token = UUID.randomUUID().toString();
                authService.putAccessToken(token, userId);
            } else {
                token = accessToken.getToken();
            }

            return new ResponseEntity<>(
                    LoginResponse.builder()
                            .accessToken(token)
                            .build(),
                    HttpStatus.OK);
        }
    }
}
