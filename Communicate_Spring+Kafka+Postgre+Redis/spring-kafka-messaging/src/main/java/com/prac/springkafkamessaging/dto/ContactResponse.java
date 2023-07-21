package com.prac.springkafkamessaging.dto;

import com.prac.springkafkamessaging.entity.Contact;
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
