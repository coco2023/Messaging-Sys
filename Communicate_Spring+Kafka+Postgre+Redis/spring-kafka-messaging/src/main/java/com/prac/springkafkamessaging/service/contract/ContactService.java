package com.prac.springkafkamessaging.service.contract;

import com.prac.springkafkamessaging.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    void save(Contact contacts);

    List<Contact> findAll(Long contactId);

    Contact findByContacterId(Long userId);

    Contact finById(Long contactListId);
}
