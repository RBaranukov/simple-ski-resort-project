package com.example.ski_resort.baranukov.dto;

import com.example.ski_resort.baranukov.entity.SkiPass;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SkiPassDTO {

    Long id;

    LocalDateTime duration;

    BigDecimal cost;

    public SkiPassDTO (SkiPass skiPass){
        this.id = skiPass.getId();
        this.duration = skiPass.getDuration();
        this.cost = skiPass.getCost();
    }
}
