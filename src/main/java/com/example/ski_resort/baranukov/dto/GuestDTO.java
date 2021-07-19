package com.example.ski_resort.baranukov.dto;

import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.SkiPass;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestDTO {

    Long id;

    String name;

    String surname;

    LocalDate birthDate;

    SkiPass skiPass;

    Coach coach;

    LocalDate dateOfVisit;
}
