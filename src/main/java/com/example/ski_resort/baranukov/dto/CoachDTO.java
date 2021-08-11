package com.example.ski_resort.baranukov.dto;

import com.example.ski_resort.baranukov.entity.Coach;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoachDTO {

    Long id;

    String name, surname, category;

    char sex;

    LocalDate birthDate;

    byte[] photo;

    Long skiPassId;

    BigDecimal skiPassCost;

    LocalDateTime skiPassDuration;

    List<GuestDTO> guests;

    public CoachDTO(Coach coach) {
        this.id = coach.getId();
        this.name = coach.getName();
        this.surname = coach.getSurname();
        this.category = coach.getCategory();
        this.sex = coach.getSex();
        this.birthDate = coach.getBirthDate();
        this.photo = coach.getPhoto();
        Optional.ofNullable(coach.getGuests())
                .ifPresent(coaches -> this.guests = coaches.stream()
                        .map(GuestDTO::new)
                        .collect(Collectors.toList()));

        Optional.ofNullable(coach.getSkiPass())
                .ifPresent(skiPass -> {
                    skiPassId = skiPass.getId();
                    skiPassCost = skiPass.getCost();
                    skiPassDuration = skiPass.getDuration();
                });
    }
}
