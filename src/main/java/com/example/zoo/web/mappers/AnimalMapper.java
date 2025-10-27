package com.example.zoo.web.mappers;


import com.example.zoo.domain.animal.Animal;
import com.example.zoo.web.dto.animal.AnimalDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    AnimalDto toDto (Animal animal);

    List<AnimalDto> toDto(List<Animal> animals);

    Animal toEntity(AnimalDto animalDto);

}