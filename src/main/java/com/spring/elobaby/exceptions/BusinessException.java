package com.spring.elobaby.exceptions;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
