package com.example.zoo.service.impl;

import com.example.zoo.domain.animal.Animal;
import com.example.zoo.domain.exception.ResourceNotFoundException;
import com.example.zoo.repository.AnimalRepository;
import com.example.zoo.service.AnimalService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Data
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    @Override
    @Transactional(readOnly = true)
    public Animal getById(Long id) {
        Optional<Animal> animal =  animalRepository.findById(id);
        return animal.orElseThrow(() -> new ResourceNotFoundException("Animal not found. ID = " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Animal> getAllByUserId(Long userId) {
        List<Animal> animals = animalRepository.findAllByUserId(userId);
        if (animals.isEmpty()) {
            throw new ResourceNotFoundException("Animals or user not found. UserId = " + userId);
        }
        return animals;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Animal animal = getById(id);
        animalRepository.delete(animal);
    }

    @Override
    @Transactional
    public Animal update(Animal animal) {
        validateBirthday(animal.getBirthDate());
        Animal existing = getById(animal.getId());
        existing.setName(animal.getName());
        existing.setType(animal.getType());
        existing.setSex(animal.getSex());
        existing.setBirthDate(animal.getBirthDate());
        return animalRepository.save(existing);
    }

    @Override
    @Transactional
    public Animal create(Animal animal, Long userId) {
        validateBirthday(animal.getBirthDate());
        animalRepository.save(animal);
        animalRepository.assignAnimal(userId, animal.getId());
        return animal;
    }


    private void validateBirthday(LocalDate localDate) {
        if (localDate.isAfter(LocalDate.now())) {
            throw new IllegalStateException("Animal birthday in future.");
        }

    }
}