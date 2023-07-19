package com.prac.springkafkamessaging.persistent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="users")

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long userId;

    @Column
    private String fname;

    @Column
    private String lname;

    @Column
    private String mobile;

    @Column(name="created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "User", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<Contact> contacts;

}
