package com.spring.elobaby.service;

import com.spring.elobaby.dal.model.dto.GameDto;
import com.spring.elobaby.dal.model.dto.PlayerScoreCreationDto;
import com.spring.elobaby.dal.model.dto.PlayerScoreDto;
import com.spring.elobaby.dal.model.postgres.Game;
import com.spring.elobaby.dal.model.postgres.PlayerScore;
import com.spring.elobaby.dal.postgres.repository.GameRepository;
import com.spring.elobaby.dal.postgres.repository.PlayerScoreRepository;
import com.spring.elobaby.dal.postgres.repository.UserRepository;
import com.spring.elobaby.exceptions.BusinessException;
import com.spring.elobaby.mapper.GameMapper;
import com.spring.elobaby.mapper.PlayerScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerScoreService {


    @Autowired
    PlayerScoreRepository playerScoreRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    public PlayerScoreDto computeEloChange(PlayerScore playerScore){
        var myScore = playerScore.getScore();
        var game = gameRepository.findById(playerScore.getGameId())
                .orElseThrow(() -> new BusinessException(
                        "Game not found"
                ));
        var otherTeamPlayerScore = game.getPlayerScores()
                .stream()
                .filter(playerScore1 -> !playerScore1.getTeam()
                        .equals(playerScore.getTeam()))
                .collect(Collectors.toList());

        var otherTeamScore = otherTeamPlayerScore.get(0).getScore();
        if(myScore > otherTeamScore){
            return computeEloVictory(playerScore);
        }else{
            return computeEloDefeat(playerScore);
        }

    }

    public PlayerScoreDto computeEloVictory(PlayerScore playerScore) {
        var game = gameRepository.findById(playerScore.getGameId())
                .orElseThrow(() -> new BusinessException(
                        "Game not found"
                ));

        var yFactor = GameService.getYFactor(game);
        var myElo = playerScore.getPlayer().getElo();


        var otherTeamPlayerScore = game.getPlayerScores()
                .stream()
                .filter(playerScore1 -> !playerScore1.getTeam()
                        .equals(playerScore.getTeam()))
                .collect(Collectors.toList());


        var otherTeamElo = 0;
        for (PlayerScore p : otherTeamPlayerScore) {
            otherTeamElo += (int) Math.pow(p.getPlayer()
                    .getElo(), 2);
        }
        otherTeamElo = (int) (Math.sqrt(otherTeamElo)/otherTeamPlayerScore.size());


        var variationElo = 0;
        if (yFactor == 7) {
            variationElo = 10 * yFactor * otherTeamElo / myElo - Math.min(10 * yFactor * (otherTeamElo /myElo) - 1,
                    Math.max((myElo - otherTeamElo) / 100, 0));

        } else if (yFactor == 6) {
            variationElo = 10 * yFactor * otherTeamElo /myElo - (myElo - otherTeamElo) / 100;

        }else if(yFactor == 4) {
            variationElo = 10 * yFactor * otherTeamElo / myElo - 2 * (myElo - otherTeamElo) / 100;
        }else {
            variationElo = 10 * yFactor * otherTeamElo / myElo - 3 * (myElo - otherTeamElo) / 100;
        }
        variationElo = Math.round(variationElo);

        playerScore.setStartElo(myElo);
        playerScore.setEndElo(Math.max(myElo + variationElo,10));

        return PlayerScoreMapper.instance()
                .convertToDto(playerScoreRepository.save(playerScore));

    }

    public PlayerScoreDto computeEloDefeat(PlayerScore playerScore) {
        var game = gameRepository.findById(playerScore.getGameId())
                .orElseThrow(() -> new BusinessException(
                        "Game not found"
                ));

        var yFactor = (double)GameService.getYFactor(game);

        var myElo = playerScore.getPlayer().getElo();



        var otherTeamPlayerScore = game.getPlayerScores()
                .stream()
                .filter(playerScore1 -> !playerScore1.getTeam()
                        .equals(playerScore.getTeam()))
                .collect(Collectors.toList());

        var otherTeamElo = 0;
        for (PlayerScore p : otherTeamPlayerScore) {
            otherTeamElo += (int) Math.pow(p.getPlayer()
                    .getElo(), 2);
        }
        otherTeamElo = (int) (Math.sqrt(otherTeamElo)/otherTeamPlayerScore.size());

        System.out.println("Y = "+yFactor);
        var variationElo = 0d;
        if(yFactor == 7) {
            variationElo = -(yFactor/100)*myElo * Math.min(2, (double)myElo/(double)otherTeamElo) + Math.min(yFactor/100*myElo,
                    Math.max(((double)otherTeamElo-(double)myElo)/300, 0));

        } else if (yFactor == 6) {
            variationElo = -(yFactor/100)*myElo * Math.min(2, (double)myElo/(double)otherTeamElo) +Math.max(((double)otherTeamElo-(double)myElo)/300, 0);

        } else if(yFactor == 4) {
            variationElo = -(yFactor/100)*myElo * Math.min(2, (double)myElo/(double)otherTeamElo) +2*Math.max(((double)otherTeamElo-(double)myElo)/300, 0);

        }else {
            variationElo = -(yFactor/100)*myElo *Math.min(2, (double)myElo/(double)otherTeamElo) +3*Math.max(((double)otherTeamElo-(double)myElo)/300, 0);

        }
        variationElo = Math.round(variationElo);

        playerScore.setStartElo(myElo);
        playerScore.setEndElo(Math.max(myElo + (int)variationElo,10));

        return PlayerScoreMapper.instance()
                .convertToDto(playerScoreRepository.save(playerScore));

    }


    public PlayerScore createPlayerScore(PlayerScoreCreationDto dto) {
        PlayerScore ps = new PlayerScore();
        ps.setScore(dto.getScore());
        ps.setTeam(dto.getTeam());
        ps.setPosition(dto.getPosition());

        var user = userRepository.findById(dto.getPlayerId()).orElseThrow(() -> new BusinessException(
                "User not found"
        ));
        ps.setPlayer(user);
        return playerScoreRepository.save(ps);

    }

    public List<PlayerScoreDto> getUserPlayerScores(Long userId) {
        var playerScores = playerScoreRepository.findByPlayer(userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(
                        "User not found"
                )));
        return playerScores.stream()
                .map(PlayerScoreMapper.instance()::convertToDto)
                .collect(Collectors.toList());

    }


}
