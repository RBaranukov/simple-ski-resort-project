package com.example.ski_resort.baranukov.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "coaches")
public class Coach extends BaseEntity {

    private String name;

    private String surname;

    private String category;

    private char sex;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(columnDefinition = "LONGBLOB", name = "photo")
    private byte[] photo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ski_pass_id")
    private SkiPass skiPass;

    @OneToMany(mappedBy = "coach",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<Guest> guests;
}
