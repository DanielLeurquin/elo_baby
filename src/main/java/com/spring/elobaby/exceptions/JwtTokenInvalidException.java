package com.spring.elobaby.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenInvalidException extends AuthenticationException {
    public JwtTokenInvalidException(String msg) {
        super(msg);
    }
}