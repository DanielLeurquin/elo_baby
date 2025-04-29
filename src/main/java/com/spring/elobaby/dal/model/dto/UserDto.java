package com.spring.elobaby.dal.model.dto;


import com.spring.elobaby.dal.model.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    Long id;
    Role role;
}
