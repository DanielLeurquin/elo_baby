package com.spring.elobaby.controller;

import com.spring.elobaby.config.jwt.TokenSet;

import com.spring.elobaby.dal.model.dto.RefreshTokenDto;
import com.spring.elobaby.dal.model.dto.UserCreationDto;
import com.spring.elobaby.dal.model.dto.UserDto;
import com.spring.elobaby.exceptions.http.HttpUnauthorizedException;

import com.spring.elobaby.service.SecurityService;
import com.spring.elobaby.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @PostMapping()
    public TokenSet auth(@RequestBody UserCreationDto dto) {
        return userService.login(dto);
    }

    @PostMapping("/register")
    public TokenSet register(@RequestBody UserCreationDto dto) {
        return userService.createUserIfNotExistAndLog(dto);
    }

    @PostMapping("/refresh")
    public TokenSet getRefreshedTokens(@CookieValue(value = "refresh-token", defaultValue = "") String refreshToken, @RequestBody RefreshTokenDto dto) {
        if (refreshToken.equals("") && (dto.getRefreshToken() == null || dto.getRefreshToken().equals("")))
            throw new HttpUnauthorizedException("refresh_token_expired");

        return securityService.refreshUserToken(dto.getRefreshToken() != null && !dto.getRefreshToken().equals("") ? dto.getRefreshToken() : refreshToken);
    }
}
