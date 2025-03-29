package com.example.ski_resort.baranukov.dto;

import com.example.ski_resort.baranukov.entity.Coach;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CoachDTO extends BaseDTO {

    private String name;
    private String surname;
    private String category;

    private char sex;

    private LocalDate birthDate;

    private byte[] photo;

    private Long skiPassId;

    private BigDecimal skiPassCost;

    private LocalDateTime skiPassDuration;

    private List<GuestDTO> guests;


    public CoachDTO(final Coach coach) {
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
                    this.skiPassId = skiPass.getId();
                    this.skiPassCost = skiPass.getCost();
                    this.skiPassDuration = skiPass.getDuration();
                });
    }
}
