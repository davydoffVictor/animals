package com.example.zoo.service;

import com.example.zoo.web.dto.auth.JwtAuthRequest;
import com.example.zoo.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse authenticateAndGenerateToken(JwtAuthRequest jwtAuthRequest);
}
