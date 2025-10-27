package com.example.zoo.web.controller;

import com.example.zoo.domain.animal.Animal;
import com.example.zoo.domain.user.User;
import com.example.zoo.service.AnimalService;
import com.example.zoo.service.UserService;
import com.example.zoo.web.dto.animal.AnimalDto;
import com.example.zoo.web.dto.user.UserDto;
import com.example.zoo.web.dto.validation.OnCreate;
import com.example.zoo.web.dto.validation.OnUpdate;
import com.example.zoo.web.mappers.AnimalMapper;
import com.example.zoo.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final AnimalService animalService;
    private final UserMapper userMapper;
    private final AnimalMapper animalMapper;

    @PostMapping
    public UserDto createNew(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        log.info("Creating new user called. Username: {}", userDto.getUsername());
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userService.create(user));
    }


    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        log.info("getById called. Id = {}", id);
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("deleteById called. Id = {}", id);
        userService.delete(id);
    }

    @GetMapping("/{id}/animals")
    public List<AnimalDto>  getAnimalsByUserId(@PathVariable Long id) {
        log.info("getAnimalsByUserId called. Id = {}", id);
        List<Animal> animals = animalService.getAllByUserId(id);
        return animalMapper.toDto(animals);
    }

    @PostMapping("/{id}/animals")
    public AnimalDto createAnimal(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody AnimalDto animalDto) {
        log.info("createAnimal called. UserId = {}" , id);
        Animal animal = animalMapper.toEntity(animalDto);
        Animal createdAnimal = animalService.create(animal, id);
        return animalMapper.toDto(createdAnimal);

    }

    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        log.info("update called. Id = {}", userDto.getId());
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }


}
