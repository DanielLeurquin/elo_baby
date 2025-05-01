package com.spring.elobaby.dal.model.dto;

import com.spring.elobaby.dal.model.enums.Position;
import com.spring.elobaby.dal.model.enums.Team;
import com.spring.elobaby.dal.model.postgres.Game;
import com.spring.elobaby.dal.model.postgres.User;
import lombok.Data;

@Data
public class PlayerScoreDto {

    Long id;

    Integer score;

    Integer startElo;
    Integer endElo;

    Team team;

    Position position;

    User player;
    Long gameId;
}
