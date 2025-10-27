package com.example.zoo.service.impl;

import com.example.zoo.domain.exception.ResourceNotFoundException;
import com.example.zoo.domain.user.User;
import com.example.zoo.repository.UserRepository;
import com.example.zoo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found. ID: " + id));
    }

    @Override
    @Transactional
    public User update(User user) {
        User existing = getById(user.getId());
        existing.setName(user.getName());
        existing.setUsername(user.getUsername());
        existing.setPassword(user.getPassword());
        return userRepository.save(existing);
    }

    @Override
    @Transactional
    public User create(User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            throw new IllegalStateException("User already exists. Username: " + foundUser.get().getUsername());
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }


}
