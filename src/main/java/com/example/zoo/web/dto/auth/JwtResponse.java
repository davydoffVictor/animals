package com.example.zoo.web.dto.auth;

import lombok.Data;

@Data
public class JwtResponse {

    private Long userId;
    private String username;
    private String accessToken;

}
