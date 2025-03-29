package com.example.ski_resort.baranukov.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "guests")
public class Guest extends BaseEntity{

    private String name;

    private String surname;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ski_pass_id")
    private SkiPass skiPass;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "visit_date")
    private LocalDate visitDate;
}
