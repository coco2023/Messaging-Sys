package com.prac.springkafkamessaging.repository.persistence;

import com.prac.springkafkamessaging.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    AccessToken findByUserId(Long userId);
}
