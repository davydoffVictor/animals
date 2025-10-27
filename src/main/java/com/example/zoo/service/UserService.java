package com.example.zoo.service;

import com.example.zoo.domain.user.User;

public interface UserService {

    User getById(Long id);

    User update(User user);

    User create(User user);

    void delete(Long id);

}
