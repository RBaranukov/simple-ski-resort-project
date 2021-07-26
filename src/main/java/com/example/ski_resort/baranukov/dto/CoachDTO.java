package com.example.ski_resort.baranukov.dto;

import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.Guest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoachDTO {

    Long id;

    String name ,surname, category;

    char sex;

    LocalDate birthDate;

    byte[] photo;

    Long skiPassId;

    BigDecimal skiPassCost;

    LocalDateTime skiPassDuration;

    List<Guest> guests;

    public CoachDTO(Coach coach) {
        this.id = coach.getId();
        this.name = coach.getName();
        this.surname = coach.getSurname();
        this.category = coach.getCategory();
        this.sex = coach.getSex();
        this.birthDate = coach.getBirthDate();
        this.photo = coach.getPhoto();
        this.guests = coach.getGuests();

        Optional.ofNullable(coach.getSkiPass())
                .ifPresent(skiPass -> {
                    skiPassId = skiPass.getId();
                    skiPassCost = skiPass.getCost();
                    skiPassDuration = skiPass.getDuration();
                });
    }
}
