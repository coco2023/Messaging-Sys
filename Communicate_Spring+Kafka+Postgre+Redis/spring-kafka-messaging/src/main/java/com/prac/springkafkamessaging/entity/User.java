package com.prac.springkafkamessaging.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user", schema = "public")
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

    @ManyToOne
    @JoinColumn
    private Contact contact;

}
