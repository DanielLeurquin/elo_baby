package com.spring.elobaby.config.jwt;

import lombok.Data;

@Data
public class TokenPayload {
    Long id;
    String role;
}
