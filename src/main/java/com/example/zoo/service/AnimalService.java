package com.example.zoo.service;

import com.example.zoo.domain.animal.Animal;


public interface AnimalService {

    Animal getById(Long id);

    Animal update(Animal animal);

    void delete(Long id);

}


