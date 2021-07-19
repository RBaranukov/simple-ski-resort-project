package com.example.ski_resort.baranukov.mapper;


import com.example.ski_resort.baranukov.dto.SkiPassDTO;
import com.example.ski_resort.baranukov.entity.SkiPass;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SkiPassMapper{

    SkiPassMapper INSTANCE = Mappers.getMapper(SkiPassMapper.class);

    SkiPassDTO toDTO(SkiPass skiPass);

    SkiPass toEntity(SkiPassDTO skiPassDTO);
}
