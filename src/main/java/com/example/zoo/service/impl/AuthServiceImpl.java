package com.example.zoo.service.impl;

import com.example.zoo.service.AuthService;
import com.example.zoo.service.UserService;
import com.example.zoo.util.JwtUtil;
import com.example.zoo.web.dto.auth.JwtAuthRequest;
import com.example.zoo.web.dto.auth.JwtResponse;
import com.example.zoo.web.security.JwtEntity;
import com.example.zoo.web.security.JwtEntityFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public JwtResponse authenticateAndGenerateToken(JwtAuthRequest jwtAuthRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(),
                        jwtAuthRequest.getPassword())
        );
        JwtEntity jwtEntity = JwtEntityFactory.create(userService.getByUsername(jwtAuthRequest.getUsername()));
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(jwtUtil.generateToken(jwtAuthRequest.getUsername(), jwtEntity.getRoles()));
        jwtResponse.setUserId(jwtEntity.getId());
        jwtResponse.setUsername(jwtEntity.getUsername());
        return jwtResponse;
    }
}
