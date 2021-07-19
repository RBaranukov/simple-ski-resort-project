package com.example.ski_resort.baranukov.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "coaches")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    @NotBlank
    String name;

    @Column(name = "surname")
    @NotBlank
    String surname;

    @Column(name = "category")
    String category;

    @Column(name = "sex")
    char sex;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "birth_date")
    LocalDate birthDate;

    @Column(columnDefinition = "LONGBLOB", name = "photo")
    byte[] photo;

    @JsonProperty(value = "skiPass_coach")
    @JsonIgnoreProperties({"guest_skiPass", "coach_skiPass"})
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ski_pass_id")
    SkiPass skiPass;

    @JsonProperty(value = "guestsList")
    @JsonIgnoreProperties({"skiPass_guest", "coach_guest"})
    @OneToMany(mappedBy = "coach", cascade = {CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    List<Guest> guests;
}
