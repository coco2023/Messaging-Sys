package com.prac.springkafkamessaging.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="contacts")

public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="contact_id")
    private Long contactId;

    @Column(name="user_id")
    private Long userId;

    @Column(name="contact_user_id")
    private Long contactUserId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_user_id", nullable = false, insertable = false, updatable = false, referencedColumnName = "user_id")
    private User user;

}
