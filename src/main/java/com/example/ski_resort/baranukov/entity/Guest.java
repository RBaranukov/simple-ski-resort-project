package com.example.ski_resort.baranukov.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String surname;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "birth_date")
    LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ski_pass_id")
    SkiPass skiPass;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "coach_id")
    Coach coach;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "date_of_visit")
    LocalDate dateOfVisit;
}
