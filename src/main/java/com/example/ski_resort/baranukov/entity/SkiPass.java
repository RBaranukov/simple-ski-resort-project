package com.example.ski_resort.baranukov.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ski_passes")
public class SkiPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ss:mm:HH dd.MM.yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "duration")
    LocalDateTime duration;

    @Column(name = "cost")
    BigDecimal cost;

    @JsonProperty(value = "guest_skiPass")
    @JsonIgnoreProperties({"skiPass_guest", "coach_guest"})
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "skiPass",
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
    Guest guest;

    @JsonProperty(value = "coach_skiPass")
    @JsonIgnoreProperties({"skiPass_coach", "guestsList"})
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "skiPass",
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
    Coach coach;
}
