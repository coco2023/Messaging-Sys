package com.prac.springkafkamessaging.auth.controller;

import com.prac.springkafkamessaging.persistent.model.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContactResponse {

    private List<Contact> contacts;

}
