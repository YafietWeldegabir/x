package com.x.authservice.Repository;

import com.x.authservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

 public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
}