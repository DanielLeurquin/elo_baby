package com.spring.elobaby.service;

import com.spring.elobaby.dal.model.dto.UserDto;
import com.spring.elobaby.dal.postgres.repository.UserRepository;
import com.spring.elobaby.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityService securityService;

    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(UserMapper.instance()::convertToDto)
                .collect(Collectors.toList());
    }
}
