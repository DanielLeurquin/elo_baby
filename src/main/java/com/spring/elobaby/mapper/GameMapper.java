package com.spring.elobaby.mapper;


import com.spring.elobaby.dal.model.dto.GameDto;
import com.spring.elobaby.dal.model.dto.UserDto;
import com.spring.elobaby.dal.model.postgres.Game;
import com.spring.elobaby.dal.model.postgres.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserMapper.class, PlayerScoreMapper.class})

public interface GameMapper {


    static GameMapper instance() {
        return Mappers.getMapper(GameMapper.class);
    }
    GameDto convertToDto(Game game);


}
