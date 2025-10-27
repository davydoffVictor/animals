package com.example.zoo.repository;

import com.example.zoo.domain.animal.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Optional<Animal> findById(Long id);

    void deleteById(Long id);

    @Query(value = """
	        select a.*
	            from animals a inner join users_animals ua on a.id = ua.animal_id
	            where ua.user_id = :userId
            """, nativeQuery = true)
    List<Animal> findAllByUserId(Long userId);

    @Modifying
    @Query(value = """
            insert into users_animals (user_id, animal_id)
            values (:userId, :animalId)
            """, nativeQuery = true)
    void assignAnimal(Long userId, Long animalId);

}