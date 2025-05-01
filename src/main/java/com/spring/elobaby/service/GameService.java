package com.spring.elobaby.service;

import com.spring.elobaby.dal.model.dto.GameCreationDto;
import com.spring.elobaby.dal.model.dto.GameDto;
import com.spring.elobaby.dal.model.dto.PlayerScoreCreationDto;
import com.spring.elobaby.dal.model.dto.PlayerScoreDto;
import com.spring.elobaby.dal.model.postgres.Game;
import com.spring.elobaby.dal.model.postgres.PlayerScore;
import com.spring.elobaby.dal.postgres.repository.GameRepository;
import com.spring.elobaby.dal.postgres.repository.UserRepository;
import com.spring.elobaby.exceptions.BusinessException;
import com.spring.elobaby.mapper.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerScoreService playerScoreService;

    @Autowired
    UserRepository userRepository;


    public static int getYFactor(Game game) {

        int scoreMax = game.getPlayerScores()
                .stream()
                .mapToInt(PlayerScore::getScore)
                .max()
                .orElseThrow(() -> new BusinessException(
                        "No player score found"
                ));

        int scoreMin = game.getPlayerScores()
                .stream()
                .mapToInt(PlayerScore::getScore)
                .min()
                .orElseThrow(() -> new BusinessException(
                        "No player score found"
                ));

        if (scoreMin == 0) {
            return 7;
        } else if (scoreMin > 0 && scoreMin <= 0.2 * scoreMax) {
            return 6;
        } else if (scoreMin > 0.2 * scoreMax && scoreMin <= 0.7 * scoreMax) {
            return 4;
        } else {
            return 3;
        }

    }

    public List<GameDto> findAll() {
        return gameRepository.findAll()
                .stream()
                .map(GameMapper.instance()::convertToDto)
                .toList();
    }

    public GameDto createGame(GameCreationDto dto) {
        Game game = new Game();
        game.setType(dto.getType());
        game.setPlayerScores(new ArrayList<PlayerScore>());
        gameRepository.save(game);
        for(PlayerScoreCreationDto playerScore : dto.getPlayerScores()) {
            var ps = playerScoreService.createPlayerScore(playerScore);
            game.getPlayerScores().add(ps);
            ps.setGameId(game.getId());
        }
        for(PlayerScore ps :  game.getPlayerScores()) {
            var psDto = playerScoreService.computeEloChange(ps);
        }
        for(PlayerScore ps :  game.getPlayerScores()) {
            ps.getPlayer().setElo(ps.getEndElo());
            userRepository.save(ps.getPlayer());
        }



        return GameMapper.instance()
                .convertToDto(gameRepository.save(game));
    }

    public List<GameDto> getUserGames(Long id) {
        var games = gameRepository.findAll()
                .stream()
                .filter(game -> game.getPlayerScores()
                        .stream()
                        .anyMatch(playerScore -> playerScore.getPlayer()
                                .getId()
                                .equals(id)))
                .toList();
        return games.stream()
                .map(GameMapper.instance()::convertToDto)
                .toList();
    }
}
