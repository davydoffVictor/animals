package com.example.zoo.web.dto.animal;

import com.example.zoo.domain.animal.Sex;
import com.example.zoo.domain.animal.Type;
import com.example.zoo.web.dto.validation.OnCreate;
import com.example.zoo.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AnimalDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private final Long id;

    @NotNull(message = "Type must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private final Type type;

    @NotNull(message = "Birthdate must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;

    @NotNull(message = "Sex must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private final Sex sex;

    @NotNull(message = "Name must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name must be < 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private final String name;

}
