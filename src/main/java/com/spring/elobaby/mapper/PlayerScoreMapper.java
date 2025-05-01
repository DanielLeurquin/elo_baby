package com.spring.elobaby.mapper;


import com.spring.elobaby.dal.model.dto.PlayerScoreDto;
import com.spring.elobaby.dal.model.postgres.PlayerScore;
import com.spring.elobaby.dal.postgres.repository.PlayerScoreRepository;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserMapper.class})

public interface PlayerScoreMapper {


    static PlayerScoreMapper instance() {
        return Mappers.getMapper(PlayerScoreMapper.class);
    }
     PlayerScoreDto convertToDto(PlayerScore playerScore);

}
