package com.spring.elobaby.dal.model.dto;

import com.spring.elobaby.dal.model.enums.GameType;
import com.spring.elobaby.dal.model.postgres.PlayerScore;
import lombok.Data;

import java.util.List;

@Data
public class GameCreationDto {
    GameType type;
    List<PlayerScoreCreationDto> playerScores;

}
