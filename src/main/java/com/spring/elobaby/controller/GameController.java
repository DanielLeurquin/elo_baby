package com.spring.elobaby.controller;

import com.spring.elobaby.constants.Roles;
import com.spring.elobaby.dal.model.dto.GameCreationDto;
import com.spring.elobaby.dal.model.dto.GameDto;
import com.spring.elobaby.service.GameService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    GameService gameService;

    @RolesAllowed({Roles.USER})
    @GetMapping()
    public ResponseEntity<List<GameDto>> getGames() {
        return ResponseEntity.ok(gameService.findAll());
    }

    @RolesAllowed({Roles.USER})
    @PostMapping()
    public ResponseEntity<GameDto> createGame(@RequestBody GameCreationDto dto) {
        return ResponseEntity.ok(gameService.createGame(dto));
    }

    @RolesAllowed({Roles.USER})
    @GetMapping("/user/{id}")
    public ResponseEntity<List<GameDto>> getUserGames(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getUserGames(id));
    }


}
