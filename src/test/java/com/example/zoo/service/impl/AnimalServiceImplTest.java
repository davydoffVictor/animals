package com.example.zoo.service.impl;

import com.example.zoo.domain.animal.Animal;
import com.example.zoo.domain.animal.Sex;
import com.example.zoo.domain.animal.Type;
import com.example.zoo.domain.exception.ResourceNotFoundException;
import com.example.zoo.repository.AnimalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AnimalServiceImplTest {

    @MockitoBean
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalServiceImpl animalService;

    @Test
    public void testGetByIdWorksForExistingId() {
        Long id = 1L;
        Animal animal = setTestAnimalAndRepositoryBehaviour(id);

        Animal testAnimal = animalService.getById(id);

        Mockito.verify(animalRepository, Mockito.times(1)).findById(id);
        Assertions.assertEquals(testAnimal, animal);
    }


    @Test
    public void testGetByIdThrowsExceptionForNonExistingId() {
        Long id = 1L;
        setTestAnimalAndRepositoryBehaviour(id);

        Long nonExistingId = 2L;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> animalService.getById(nonExistingId));
        Mockito.verify(animalRepository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void testGetAllByUserIdWorksForExistingId() {
        Long userId = 1L;
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            animals.add(new Animal());
        }
        Mockito.when(animalRepository.findAllByUserId(userId))
                .thenReturn(animals);

        List<Animal> testAnimals = animalService.getAllByUserId(userId);

        Assertions.assertEquals(animals, testAnimals);
        Mockito.verify(animalRepository, Mockito.times(1)).findAllByUserId(userId);

    }

    @Test
    public void testGetAllByUserIdThrowsExceptionForNonExistingId() {
        Long userId = 1L;
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            animals.add(new Animal());
        }
        Mockito.when(animalRepository.findAllByUserId(userId))
                .thenReturn(animals);
        Long nonExistingId = 2L;

        Assertions.assertThrows(ResourceNotFoundException.class, () -> animalService.getAllByUserId(nonExistingId));
        Mockito.verify(animalRepository, Mockito.times(1)).findAllByUserId(nonExistingId);
    }

    @Test
    public void testDeleteWorksForExistingId() {
        Long id = 1L;
        Animal animal = setTestAnimalAndRepositoryBehaviour(id);
        Mockito.doNothing().when(animalRepository).delete(animal);

        animalService.delete(id);

        Mockito.verify(animalRepository, Mockito.times(1)).delete(animal);
    }

    @Test
    public void testDeleteThrowsExceptionForNonExistingId() {
        Long id = 1L;
        Long nonExistingId = 2L;
        Animal animal = setTestAnimalAndRepositoryBehaviour(id);
        animal.setId(id);
        Mockito.doNothing().when(animalRepository).delete(animal);


        Assertions.assertThrows(ResourceNotFoundException.class, () -> animalService.delete(nonExistingId));
        Mockito.verify(animalRepository, Mockito.times(1)).findById(nonExistingId);
    }


    @Test
    public void testUpdateWorksForValidRequest() {
        Long id = 1L;
        Animal animal = setTestAnimalAndRepositoryBehaviour(id, Type.DOG, LocalDate.of(2018, 10, 11), Sex.FEMALE, "Lucky");
        Animal updateAnimal = new Animal();
        updateAnimal.setId(animal.getId());
        updateAnimal.setType(animal.getType());
        updateAnimal.setBirthDate(animal.getBirthDate());
        updateAnimal.setSex(animal.getSex());
        updateAnimal.setName("Charlie");
        Mockito.when(animalRepository.save(updateAnimal))
                .thenReturn(updateAnimal);

        Animal returnedAnimal = animalService.update(updateAnimal);

        Assertions.assertEquals(updateAnimal, returnedAnimal);
        Mockito.verify(animalRepository, Mockito.times(1)).save(updateAnimal);
    }

    @Test
    public void testUpdateThrowsExceptionForBirthDateInFuture() {
        Long id = 1L;
        Animal animal = setTestAnimalAndRepositoryBehaviour(id, Type.DOG, LocalDate.of(2018, 10, 11), Sex.FEMALE, "Lucky");
        Animal updateAnimal = new Animal();
        updateAnimal.setId(animal.getId());
        updateAnimal.setType(animal.getType());
        updateAnimal.setBirthDate(LocalDate.now().plusMonths(1)); //1 month in future
        updateAnimal.setSex(animal.getSex());
        updateAnimal.setName(animal.getName());
        Mockito.when(animalRepository.save(updateAnimal))
                .thenReturn(updateAnimal);

        Assertions.assertThrows(IllegalStateException.class, () -> animalService.update(updateAnimal));
        Mockito.verify(animalRepository, Mockito.never()).save(updateAnimal);
    }

    @Test
    public void testUpdateThrowsExceptionForNonExistingId() {
        Long id = 1L;
        Long nonExistingId = 2L;
        Animal animal = setTestAnimalAndRepositoryBehaviour(id, Type.DOG, LocalDate.of(2018, 10, 11), Sex.FEMALE, "Lucky");
        Animal updateAnimal = new Animal();
        updateAnimal.setId(nonExistingId);
        updateAnimal.setType(animal.getType());
        updateAnimal.setBirthDate(animal.getBirthDate());
        updateAnimal.setSex(animal.getSex());
        updateAnimal.setName(animal.getName());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> animalService.update(updateAnimal));
        Mockito.verify(animalRepository, Mockito.never()).save(updateAnimal);
    }

    @Test
    public void testCreateWorksForValidRequest() {

        Long animalToBeCreatedFutureId = 1L;
        Long userIdToBeAssignedToAnimal = 1L;
        Animal animalToBeCreated = setTestAnimalAndRepositoryBehaviour(null);
        Mockito.doAnswer(invocation -> {
                    Animal savedAnimal = invocation.getArgument(0);
                    savedAnimal.setId(animalToBeCreatedFutureId);
                    return savedAnimal;
                })
                .when(animalRepository).save(animalToBeCreated);
        Mockito.doNothing().when(animalRepository).assignAnimal(userIdToBeAssignedToAnimal, animalToBeCreatedFutureId);

        Animal createdAnimal = animalService.create(animalToBeCreated, userIdToBeAssignedToAnimal);

        Mockito.verify(animalRepository, Mockito.times(1)).save(animalToBeCreated);
        Mockito.verify(animalRepository, Mockito.times(1)).assignAnimal(userIdToBeAssignedToAnimal, animalToBeCreatedFutureId);
        Assertions.assertEquals(createdAnimal.getId(), animalToBeCreatedFutureId);


    }

    @Test
    public void testCreateThrowsExceptionForBirthDateInFuture() {
        Long userIdToBeAssignedToAnimal = 1L;
        Animal animalToBeCreated = setTestAnimalAndRepositoryBehaviour(null);
        animalToBeCreated.setBirthDate(LocalDate.now().plusMonths(5));

        Assertions.assertThrows(IllegalStateException.class, () -> animalService.create(animalToBeCreated, userIdToBeAssignedToAnimal));
    }

    @Test
    public void testPrivateMethodValidateBirthday() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method validateBirthday = AnimalServiceImpl.class.getDeclaredMethod("validateBirthday", LocalDate.class);
        validateBirthday.setAccessible(true);

        Assertions.assertDoesNotThrow(() -> validateBirthday.invoke(animalService, LocalDate.now().minusDays(1)));
        Assertions.assertThrows(InvocationTargetException.class, () -> validateBirthday.invoke(animalService, LocalDate.now().plusDays(1)));
    }




    private Animal setTestAnimalAndRepositoryBehaviour(Long id, Type type, LocalDate birthDate, Sex sex, String name) {
        Animal animal = new Animal();
        animal.setId(id);
        animal.setType(type);
        animal.setBirthDate(birthDate);
        animal.setSex(sex);
        animal.setName(name);

        Mockito.when(animalRepository.findById(id))
                .thenReturn(Optional.of(animal));

        return animal;
    }

    private Animal setTestAnimalAndRepositoryBehaviour(Long id) {
        return setTestAnimalAndRepositoryBehaviour(id, Type.CAT, LocalDate.of(2020, 11, 15), Sex.MALE, "Murzik");
    }

}
