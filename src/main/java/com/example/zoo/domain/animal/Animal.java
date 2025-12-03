package com.example.zoo.domain.animal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "animals")
@Getter
@Setter
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    private LocalDate birthDate;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    private String name;
}
