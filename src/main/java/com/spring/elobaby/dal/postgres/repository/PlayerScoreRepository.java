package com.spring.elobaby.dal.postgres.repository;

import com.spring.elobaby.dal.model.postgres.PlayerScore;
import com.spring.elobaby.dal.model.postgres.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface PlayerScoreRepository extends JpaRepository<PlayerScore, Long> {

    List<PlayerScore> findByPlayer(User player);
}
