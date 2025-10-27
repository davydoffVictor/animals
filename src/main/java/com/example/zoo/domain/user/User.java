package com.example.zoo.domain.user;

import com.example.zoo.domain.animal.Animal;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    @Transient
    private String passwordConfirmation;


    @OneToMany
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "animal_id"))
    private List<Animal> animals;

}
