package com.prac.springkafkamessaging.repository.persistence;

import com.prac.springkafkamessaging.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u, AccessToken t WHERE u.userId=t.userId AND t.token = :token")
    User findByToken(@Param("token") String token);

    User findByMobile(String mobile);

}
