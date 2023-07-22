package com.prac.springkafkamessaging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.springkafkamessaging.dto.*;
import com.prac.springkafkamessaging.entity.Contact;
import com.prac.springkafkamessaging.service.auth.AuthService;
import com.prac.springkafkamessaging.repository.cache.CacheRepository;
import com.prac.springkafkamessaging.entity.AccessToken;
import com.prac.springkafkamessaging.entity.User;
import com.prac.springkafkamessaging.repository.persistence.UserRepository;
import com.prac.springkafkamessaging.service.contract.ContactService;
import com.prac.springkafkamessaging.util.StringHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.beans.BeanUtils.copyProperties;

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

    @Autowired
    private ContactService contactService;

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

//    @RequestMapping(value = "/getcontacts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<Object> getContacts(@Valid @RequestBody ContactRequest activationRequest) {
//
//        // save the activation code to the cache repository (cached auth token)
//        User user = userRepository.findByToken(activationRequest.getAccessToken());
//
//        ContactResponse contactResponse = ContactResponse.builder()
//                .contacts(user.getContact())
//                .build();
//
//        return new ResponseEntity<>(
//                contactResponse,
//                HttpStatus.OK
//        );
//    }

    @PostMapping("/addcontacts")
    public ResponseEntity<Object> addContacts(@RequestBody ContactRequest contactRequest ) {

        // find current user by token
        User currentUser = userRepository.findByToken(contactRequest.getAccessToken());
        log.info("current user: " + currentUser);

        // get added contact user info
        User addUser = userRepository.findByMobile(contactRequest.getMobile());
        log.info("add contact user: " + addUser);

        if (currentUser.getContact() == null) {
            log.info("****current user dont have contact****");
            // create new contact list
            List<User> userList = new ArrayList<>();
            userList.add(addUser);

            Contact addContact = Contact.builder()
                    .contacterId(currentUser.getUserId())
                    .user(userList)
                    .build();
            contactService.save(addContact);

            // update current User's contact
            User user = User.builder()
                    .userId(currentUser.getUserId())
                    .mobile(currentUser.getMobile())
                    .fname(currentUser.getFname())
                    .lname(currentUser.getLname())
                    .createdAt(currentUser.getCreatedAt())
                    .contact(addContact)
                    .build();

            userRepository.save(user);
            copyProperties(currentUser, user);

            ContactResponse response = ContactResponse.builder()
                            .contacts(addContact)
                                    .build();

            log.info("ContactResponse:" + response);

            log.info("update success~" + user.getContact().getUser());
        }
        else {
            log.info("****current user has contact****");
            // get currentUser's contact List ID
            Long contactListId = currentUser.getContact().getContactListId();
            log.info("ContactListId:" + contactListId);
            Contact currentContact = contactService.finById(contactListId);
            log.info("currentContact:" + currentContact.getUser());

            currentContact.addUser(addUser);

            // update current User's contact
            User user = User.builder()
                    .userId(currentUser.getUserId())
                    .mobile(currentUser.getMobile())
                    .fname(currentUser.getFname())
                    .lname(currentUser.getLname())
                    .createdAt(currentUser.getCreatedAt())
                    .contact(currentContact)
                    .build();

            userRepository.save(user);
            copyProperties(currentUser, user);

            log.info("update success***" + user.getContact().getUser());
        }

        return new ResponseEntity<>(
                "Successfully added contact!",
                HttpStatus.OK
        );
    }

}
