package com.example.zoo.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private final LocalDateTime timestamp;
    private final String message;
    private Map<String, String> errorDetails;


}
