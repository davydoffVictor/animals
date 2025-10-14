package com.example.zoo.web.controller;

import com.example.zoo.domain.animal.Animal;
import com.example.zoo.service.AnimalService;
import com.example.zoo.web.dto.animal.AnimalDto;
import com.example.zoo.web.dto.validation.OnUpdate;
import com.example.zoo.web.mappers.AnimalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/animals")
@RequiredArgsConstructor
@Validated
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalMapper animalMapper;


    @GetMapping("/{id}")
    public AnimalDto getById(@PathVariable Long id) {
        Animal animal = animalService.getById(id);
        return animalMapper.toDto(animal);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        animalService.delete(id);
    }

    @PutMapping
    public AnimalDto update(@Validated(OnUpdate.class) @RequestBody AnimalDto animalDto) {
        Animal animal = animalMapper.toEntity(animalDto);
        Animal updatedAnimal = animalService.update(animal);
        return animalMapper.toDto(updatedAnimal);
    }




}