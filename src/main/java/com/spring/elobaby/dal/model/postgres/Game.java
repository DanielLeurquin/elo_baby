package com.spring.elobaby.dal.model.postgres;

import com.spring.elobaby.dal.model.enums.GameType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    LocalDateTime date;

    @Enumerated(EnumType.STRING)
    GameType type;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "gameId",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PlayerScore> playerScores;


}
