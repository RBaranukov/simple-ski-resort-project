package com.example.ski_resort.baranukov.dto;

import com.example.ski_resort.baranukov.entity.SkiPass;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class SkiPassDTO extends BaseDTO{

    private LocalDateTime duration;

    private BigDecimal cost;

    public SkiPassDTO (final SkiPass skiPass){
        this.id = (skiPass.getId());
        this.duration = skiPass.getDuration();
        this.cost = skiPass.getCost();
    }
}
