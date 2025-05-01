package com.spring.elobaby.controller;


import com.spring.elobaby.constants.Roles;
import com.spring.elobaby.dal.model.dto.PlayerScoreCreationDto;
import com.spring.elobaby.dal.model.dto.PlayerScoreDto;
import com.spring.elobaby.service.PlayerScoreService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playerScores")
public class PlayerScoreController {

    @Autowired
    PlayerScoreService playerScoreService;

    @RolesAllowed({Roles.USER})
    @GetMapping("/{id}")
    public ResponseEntity<List<PlayerScoreDto>> getPlayerScore(@PathVariable Long id) {
        return ResponseEntity.ok(playerScoreService.getUserPlayerScores(id));
    }


}
