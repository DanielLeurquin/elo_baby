package com.spring.elobaby.dal.model.dto;

import com.spring.elobaby.dal.model.enums.Position;
import com.spring.elobaby.dal.model.enums.Team;
import lombok.Data;

@Data
public class PlayerScoreCreationDto {

    Integer score;
    Team team;
    Position position;
    Long playerId;

}
