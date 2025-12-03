package com.example.zoo.service.impl;

import com.example.zoo.domain.exception.ResourceNotFoundException;
import com.example.zoo.domain.user.Role;
import com.example.zoo.domain.user.User;
import com.example.zoo.repository.UserRepository;
import com.example.zoo.service.UserService;
import io.jsonwebtoken.security.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found. ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found"));
    }

    @Override
    @Transactional
    public User update(User user) {
        User existing = getById(user.getId());
        existing.setName(user.getName());
        existing.setUsername(user.getUsername());
        existing.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(existing);
    }

    @Override
    @Transactional
    public User create(User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            throw new IllegalStateException("User already exists. Username: " + foundUser.get().getUsername());
        }
        if(!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation don't match.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.ROLE_USER));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User createAdmin(User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            throw new IllegalStateException("User already exists. Username: " + foundUser.get().getUsername());
        }
        if(!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation don't match.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.ROLE_ADMIN));
        return userRepository.save(user);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    @Override
    public boolean isAnimalOwner(Long userId, Long animalId) {
        return userRepository.isAnimalOwner(userId, animalId);
    }


}
