package com.example.ski_resort.baranukov.dto;

import com.example.ski_resort.baranukov.entity.Guest;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@ToString
@NoArgsConstructor
public class GuestDTO extends BaseDTO{

    private Long skiPassId;
    private Long coachId;

    private String name;
    private String surname;
    private String coachNameSurname;
    private String coachCategory;

    private LocalDate birthDate;

    private char coachSex;

    private BigDecimal skiPassCost;

    private LocalDateTime skiPassDuration;

    private LocalDate visitDate;

    public GuestDTO(final Guest guest) {
        this.id = guest.getId();
        this.name = guest.getName();
        this.surname = guest.getSurname();
        this.birthDate = guest.getBirthDate();
        this.visitDate = guest.getVisitDate();

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
