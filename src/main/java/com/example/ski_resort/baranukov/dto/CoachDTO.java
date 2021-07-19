package com.example.ski_resort.baranukov.dto;


import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.entity.SkiPass;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoachDTO {
    Long id;

    String name;

    String surname;

    String category;

    char sex;

    LocalDate birthDate;

    byte[] photo;

    SkiPass skiPass;

    List<Guest> guests;
}
