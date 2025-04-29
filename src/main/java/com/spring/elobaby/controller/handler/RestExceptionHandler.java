package com.spring.elobaby.controller.handler;

import com.spring.elobaby.dal.model.dto.ErrorDto;
import com.spring.elobaby.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        log.error(Arrays.toString(ex.getStackTrace()));

        var error = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        log.error(Arrays.toString(ex.getStackTrace()));

        var error = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        var stringBuilder = new StringBuilder("");
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err -> stringBuilder.append(err.getField()).append(" ").append(err.getDefaultMessage()).append(". "));

        var error = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(stringBuilder.toString())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
