package com.example.ski_resort.baranukov.mapper;


import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.entity.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    GuestMapper INSTANCE = Mappers.getMapper(GuestMapper.class);

    GuestDTO toDTO(Guest guest);

    Guest toEntity(GuestDTO guestDTO);
}
