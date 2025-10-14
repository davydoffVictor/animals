package com.example.zoo.web.mappers;


import com.example.zoo.domain.animal.Animal;
import com.example.zoo.web.dto.animal.AnimalDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    AnimalDto toDto (Animal animal);

    Animal toEntity(AnimalDto animalDto);

}