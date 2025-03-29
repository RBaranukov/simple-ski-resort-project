package com.example.ski_resort.baranukov.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "ski_passes")
public class SkiPass extends BaseEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ss:mm:HH dd.MM.yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime duration;

    private BigDecimal cost;

    @OneToOne(mappedBy = "skiPass", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Guest guest;

    @OneToOne(mappedBy = "skiPass", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Coach coach;
}
