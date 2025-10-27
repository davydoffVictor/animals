package com.example.zoo.service.impl;

import com.example.zoo.domain.exception.ResourceNotFoundException;
import com.example.zoo.domain.user.User;
import com.example.zoo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.Optional;

@SpringBootTest
public class UserServiceImplTest {
    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testGetByIdWorksForExistingId() {
        Long id = 1L;
        User user = setTestUserAndRepositoryBehaviour(id);

        User returnedUser = userService.getById(id);

        Assertions.assertEquals(user, returnedUser);
    }

    @Test
    public void testGetByIdThrowsExceptionForNonExistingId() {
        Long id = 1L;
        User user = setTestUserAndRepositoryBehaviour(id);
        Long nonExistingId = 2L;

        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getById(nonExistingId));
    }


    @Test
    public void testUpdateWorksForValidRequest() {
        Long id = 1L;
        User user = setTestUserAndRepositoryBehaviour(id);
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setName(user.getName());
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(user.getPassword());
        updateUser.setName("Maxim Grebenshchikov");
        Mockito.when(userRepository.save(updateUser))
                .thenReturn(updateUser);

        User returnedUser = userService.update(updateUser);

        Assertions.assertEquals(updateUser, returnedUser);
        Mockito.verify(userRepository, Mockito.times(1)).save(updateUser);
    }

    @Test
    public void testUpdateThrowsExceptionForNonExistingId() {
        Long id = 1L;
        Long nonExistingId = 2L;
        User user = setTestUserAndRepositoryBehaviour(id);
        User updateUser = new User();
        updateUser.setId(nonExistingId);
        updateUser.setName(user.getName());
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(user.getPassword());
        updateUser.setName("Maxim Grebenshchikov");
        Mockito.when(userRepository.save(updateUser))
                .thenReturn(updateUser);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.update(updateUser));
    }

    @Test
    public void testCreateWorksForValidRequest() {

        Long userToBeCreatedFutureId = 1L;
        User userToBeCreated = setTestUserAndRepositoryBehaviour(null);
        Mockito.doAnswer(invocation -> {
                    User savedUser = invocation.getArgument(0);
                    savedUser.setId(userToBeCreatedFutureId);
                    return savedUser;
                })
                .when(userRepository).save(userToBeCreated);

        User createdUser = userService.create(userToBeCreated);

        Mockito.verify(userRepository, Mockito.times(1)).save(userToBeCreated);
        Assertions.assertEquals(createdUser.getId(), userToBeCreatedFutureId);
    }

    @Test
    public void testDeleteWorksForExistingId() {
        Long id = 1L;
        User user = setTestUserAndRepositoryBehaviour(id);
        Mockito.doNothing().when(userRepository).delete(user);

        userService.delete(id);

        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    public void testDeleteThrowsExceptionForNonExistingId() {
        Long id = 1L;
        Long nonExistingId = 2L;
        User user = setTestUserAndRepositoryBehaviour(id);
        user.setId(id);
        Mockito.doNothing().when(userRepository).delete(user);


        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.delete(nonExistingId));
        Mockito.verify(userRepository, Mockito.times(1)).findById(nonExistingId);
    }



    private User setTestUserAndRepositoryBehaviour(Long id, String name, String username, String password) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(user));
        return user;
    }

    private User setTestUserAndRepositoryBehaviour(Long id) {
        return setTestUserAndRepositoryBehaviour(id, "ILya Ryabov", "iryabov@mail.ru", "123");
    }





}
