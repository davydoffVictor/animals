package com.example.zoo.service.impl;

import com.example.zoo.domain.animal.Animal;
import com.example.zoo.repository.AnimalRepository;
import com.example.zoo.service.AnimalService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@Data
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    @Override
    @Transactional(readOnly = true)
    public Animal getById(Long id) {
        Optional<Animal> animal =  animalRepository.findById(id);
        return animal.orElseThrow(() -> new NoSuchElementException("Animal not found id = " + id)); //TODO exception handler
    }

    @Override
    @Transactional(readOnly = true)
    public List<Animal> getAllByUserId(Long userId) {
        return animalRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        animalRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Animal update(Animal animal) {
        return animalRepository.save(animal);
    }


}