package com.prac.springkafkamessaging.persistent.repository;

import com.prac.springkafkamessaging.persistent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
