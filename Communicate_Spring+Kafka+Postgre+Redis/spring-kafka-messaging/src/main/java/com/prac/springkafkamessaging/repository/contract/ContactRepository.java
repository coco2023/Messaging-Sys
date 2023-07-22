package com.prac.springkafkamessaging.repository.contract;

import com.prac.springkafkamessaging.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findByContacterId(Long userId);
}
