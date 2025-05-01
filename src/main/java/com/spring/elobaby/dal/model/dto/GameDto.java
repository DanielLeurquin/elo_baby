package com.spring.elobaby.dal.model.dto;

import com.spring.elobaby.dal.model.enums.GameType;
import com.spring.elobaby.dal.model.postgres.PlayerScore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class GameDto {

    Long id;
    LocalDateTime date;
    GameType type;
    List<PlayerScore> playerScores;

}
