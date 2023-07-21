package com.prac.springkafkamessaging.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="access_token")

public class AccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long token_id;

    @Column(name="token")
    private String token;

    @Column(name="user_id")
    private Long userId;

    @Column(name="created_at")
    private Date createdAt;

}
