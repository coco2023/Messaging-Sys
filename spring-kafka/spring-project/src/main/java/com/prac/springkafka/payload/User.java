package com.prac.springkafka.payload;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class User {

    public int id;

    private String firstName;

    private String lastName;

}
