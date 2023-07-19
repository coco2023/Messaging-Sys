package com.prac.springkafkamessaging.persistent.repository;

import com.prac.springkafkamessaging.persistent.model.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
}
