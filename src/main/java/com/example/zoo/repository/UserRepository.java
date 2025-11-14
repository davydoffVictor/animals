package com.example.zoo.repository;

import com.example.zoo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    //TODO make index for username
    Optional<User> findByUsername(String username);

}
