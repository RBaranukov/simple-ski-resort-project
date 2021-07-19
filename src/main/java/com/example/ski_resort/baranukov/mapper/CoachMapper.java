package com.example.ski_resort.baranukov.mapper;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.entity.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    CoachMapper INSTANCE = Mappers.getMapper(CoachMapper.class);

    CoachDTO toDTO(Coach coach);

    Coach toEntity(CoachDTO coachDTO);
}
