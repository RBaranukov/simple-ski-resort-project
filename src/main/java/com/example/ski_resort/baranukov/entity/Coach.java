package com.example.ski_resort.baranukov.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "coaches")
public class Coach extends BaseEntity{

    String name;

    String surname;

    String category;

    char sex;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "birth_date")
    LocalDate birthDate;

    @Column(columnDefinition = "LONGBLOB", name = "photo")
    byte[] photo;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "ski_pass_id")
    SkiPass skiPass;

    @OneToMany(mappedBy = "coach",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    List<Guest> guests;
}
