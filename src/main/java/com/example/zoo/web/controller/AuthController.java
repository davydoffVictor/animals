package com.example.zoo.web.controller;

import com.example.zoo.domain.user.User;
import com.example.zoo.service.AuthService;
import com.example.zoo.service.UserService;
import com.example.zoo.web.dto.auth.JwtAuthRequest;
import com.example.zoo.web.dto.auth.JwtResponse;
import com.example.zoo.web.dto.user.UserDto;
import com.example.zoo.web.dto.validation.OnCreate;
import com.example.zoo.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse generateToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
        log.info("authentication requested for user: {}", jwtAuthRequest.getUsername());
        return authService.authenticateAndGenerateToken(jwtAuthRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        log.info("Creating new user called. Username: {}", userDto.getUsername());
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userService.create(user));
    }

    @PostMapping("/createAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto createAdmin(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        log.info("Creating new admin user called. Username: {}", userDto.getUsername());
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userService.createAdmin(user));
    }

}
