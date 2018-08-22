package com.apress.spring.repository;

import com.apress.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by simon on 22/08/18.
 */



public interface UserDetailsDao  extends JpaRepository<User, Long> {
    User findByUsername(String username);
}