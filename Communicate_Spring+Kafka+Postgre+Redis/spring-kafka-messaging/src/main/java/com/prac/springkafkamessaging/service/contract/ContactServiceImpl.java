package com.prac.springkafkamessaging.service.contract;

import com.prac.springkafkamessaging.entity.Contact;
import com.prac.springkafkamessaging.repository.contract.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void save(Contact contacts) {
        contactRepository.save(contacts);
    }

    @Override
    public List<Contact> findAll(Long contactId) {
        return contactRepository.findAll();
    }

    @Override
    public Contact findByContacterId(Long userId) {
        return contactRepository.findByContacterId(userId);
    }

    @Override
    public Contact finById(Long contactListId) {
        return contactRepository.findById(contactListId)
                .orElseThrow(() -> new RuntimeException("No contact exist!"));
    }
}
