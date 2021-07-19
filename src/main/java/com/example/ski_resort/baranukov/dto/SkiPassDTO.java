package com.example.ski_resort.baranukov.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SkiPassDTO {

    Long id;

    LocalDateTime duration;

    Double cost;
}
