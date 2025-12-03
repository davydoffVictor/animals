package com.example.zoo.web.controller;

import com.example.zoo.domain.animal.Animal;
import com.example.zoo.service.AnimalService;
import com.example.zoo.web.dto.animal.AnimalDto;
import com.example.zoo.web.dto.validation.OnUpdate;
import com.example.zoo.web.mappers.AnimalMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/animals")
@RequiredArgsConstructor
@Validated
public class AnimalController {

    private static final Logger log = LoggerFactory.getLogger(AnimalController.class);


    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    @GetMapping("/{id}")
    @PreAuthorize("@cse.canAccessAnimal(#id)")
    public AnimalDto getById(@PathVariable Long id) {
        log.info("getById called. Id = {}", id);
        Animal animal = animalService.getById(id);
        return animalMapper.toDto(animal);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@cse.canAccessAnimal(#id)")
    public void deleteById(@PathVariable Long id) {
        log.info("deleteById called. Id = {}", id);
        animalService.delete(id);
    }

    @PutMapping
    @PreAuthorize("@cse.canAccessAnimal(#animalDto.id)")
    public AnimalDto update(@Validated(OnUpdate.class) @RequestBody AnimalDto animalDto) {
        log.info("update called. Id = {}", animalDto.getId());
        Animal animal = animalMapper.toEntity(animalDto);
        Animal updatedAnimal = animalService.update(animal);
        return animalMapper.toDto(updatedAnimal);
    }




}