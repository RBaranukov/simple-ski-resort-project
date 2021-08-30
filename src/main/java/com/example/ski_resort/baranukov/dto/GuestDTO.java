package com.example.ski_resort.baranukov.dto;

import com.example.ski_resort.baranukov.entity.Guest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestDTO {

    Long id, skiPassId, coachId;

    String name, surname, coachNameSurname, coachCategory;

    LocalDate birthDate;

    char coachSex;

    BigDecimal skiPassCost;

    LocalDateTime skiPassDuration;

    LocalDate dateOfVisit;

    public GuestDTO(Guest guest) {
        this.id = guest.getId();
        this.name = guest.getName();
        this.surname = guest.getSurname();
        this.birthDate = guest.getBirthDate();
        this.dateOfVisit = guest.getDateOfVisit();

        Optional.ofNullable(guest.getCoach())
                .ifPresent(coach -> {
                    coachId = coach.getId();
                    coachNameSurname = coach.getName() + " " + coach.getSurname();
                    coachCategory = coach.getCategory();
                    coachSex = coach.getSex();
                });

        Optional.ofNullable(guest.getSkiPass())
                .ifPresent(skiPass -> {
                    skiPassId = skiPass.getId();
                    skiPassCost = skiPass.getCost();
                    skiPassDuration = skiPass.getDuration();
                });
    }
}
