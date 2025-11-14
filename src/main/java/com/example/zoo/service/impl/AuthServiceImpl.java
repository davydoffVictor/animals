package com.example.zoo.service.impl;

import com.example.zoo.domain.user.User;
import com.example.zoo.service.AuthService;
import com.example.zoo.service.UserService;
import com.example.zoo.util.JwtUtil;
import com.example.zoo.web.dto.auth.JwtAuthRequest;
import com.example.zoo.web.dto.auth.JwtResponse;
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
            User user = userService.getByUsername(jwtAuthRequest.getUsername());
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(jwtUtil.generateToken(jwtAuthRequest.getUsername()));
            jwtResponse.setUserId(user.getId());
            jwtResponse.setUsername(user.getUsername());
            return jwtResponse;
    }
}
