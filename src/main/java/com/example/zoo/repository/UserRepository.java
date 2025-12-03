package com.example.zoo.repository;

import com.example.zoo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    //TODO make index for username
    Optional<User> findByUsername(String username);

    @Query(value = """
	        select u.name
	            from user
	            where u.name = :username
            """, nativeQuery = true)
    String checkUsername(String username);

    @Query(value = """
             SELECT exists(
                           SELECT 1
                           FROM users_animals
                           WHERE user_id = :userId
                             AND animal_id = :animalId)
            """, nativeQuery = true)
    boolean isAnimalOwner(Long userId, Long animalId);
}
