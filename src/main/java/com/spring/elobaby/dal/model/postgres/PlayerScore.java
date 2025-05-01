package com.spring.elobaby.dal.model.postgres;

import com.spring.elobaby.dal.model.enums.Position;
import com.spring.elobaby.dal.model.enums.Team;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class PlayerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer score;

    Integer startElo;
    Integer endElo;

    @Enumerated(EnumType.STRING)
    Team team;

    @Enumerated(EnumType.STRING)
    Position position;

    Long gameId;

    @ManyToOne
    @JoinColumn(name = "player_id")
    User player;



}
