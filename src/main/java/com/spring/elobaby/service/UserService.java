package com.spring.elobaby.service;

import com.spring.elobaby.config.jwt.TokenPayload;
import com.spring.elobaby.config.jwt.TokenSet;
import com.spring.elobaby.dal.model.dto.UserCreationDto;
import com.spring.elobaby.dal.model.dto.UserDto;
import com.spring.elobaby.dal.model.enums.Role;
import com.spring.elobaby.dal.model.postgres.User;
import com.spring.elobaby.dal.postgres.repository.UserRepository;
import com.spring.elobaby.exceptions.BusinessException;
import com.spring.elobaby.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public TokenSet createUserIfNotExistAndLog(UserCreationDto dto) {
        var userOpt = userRepository.findByUsername(dto.getUsername().strip());
        if (userOpt.isPresent()) {
            throw new BusinessException("Cet utilisateur existe déjà");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setElo(500);
        user.setRole(Role.ROLE_USER);
        user = userRepository.save(user);
        return securityService.logUser(user);

    }

    public TokenSet login(UserCreationDto dto){
        var userOpt = userRepository.findByUsername(dto.getUsername());
        if (userOpt.isPresent()) {
            return securityService.logUser(userOpt.get());
        }else {
            throw new BusinessException("User not found");
        }
    }


    public UserDto getActiveStudent() {

        var sec = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var payload = (TokenPayload) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        var userid = payload.getId();

        var user = userRepository
                .findById(userid)
                .map(UserMapper.instance()::convertToDto)
                .orElseThrow(() -> new BusinessException("Unable to find the active user"));

        return user;

    }


    public UserDto findByUsername(String username) {
        var user = userRepository
                .findByUsername(username)
                .map(UserMapper.instance()::convertToDto)
                .orElseThrow(() -> new BusinessException("Unable to find the active user"));

        return user;
    }
}
