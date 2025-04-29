package com.spring.elobaby.controller;


import com.spring.elobaby.constants.Roles;
import com.spring.elobaby.dal.model.dto.UserDto;

import com.spring.elobaby.service.SecurityService;
import com.spring.elobaby.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService service;

    @GetMapping()
    @RolesAllowed({Roles.ADMIN})
    public List<UserDto> findAll(){
        return userService.findAll();
    }


}
