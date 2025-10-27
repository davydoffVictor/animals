package com.example.zoo.service;

import com.example.zoo.domain.animal.Animal;

import java.util.List;


public interface AnimalService {

    Animal getById(Long id);

    List<Animal> getAllByUserId(Long userId);

    Animal update(Animal animal);

    Animal create(Animal animal, Long userId);

    void delete(Long id);


}


