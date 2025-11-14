package com.example.zoo.web.controller;

import com.example.zoo.service.AuthService;
import com.example.zoo.web.dto.auth.JwtAuthRequest;
import com.example.zoo.web.dto.auth.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public JwtResponse generateToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
        return authService.authenticateAndGenerateToken(jwtAuthRequest);
    }
}
