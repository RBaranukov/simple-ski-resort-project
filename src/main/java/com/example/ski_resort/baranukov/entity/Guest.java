package com.example.ski_resort.baranukov.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd.MM.yyyy")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference(value = "guestSkiPass")
    @JoinColumn(name = "ski_pass_id")
    private SkiPass skiPass;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd.MM.yyyy")
    @Column(name = "date_of_visit")
    private LocalDate dateOfVisit;

    public Guest() {
    }

    public Guest(String name, String surname, LocalDate birthDate, SkiPass skiPass, Coach coach, LocalDate dateOfVisit) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.skiPass = skiPass;
        this.coach = coach;
        this.dateOfVisit = dateOfVisit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public SkiPass getSkiPass() {
        return skiPass;
    }

    public void setSkiPass(SkiPass skiPass) {
        this.skiPass = skiPass;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public LocalDate getDateOfVisit() {
        dateOfVisit.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return dateOfVisit;
    }

    public void setDateOfVisit(LocalDate dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(id, guest.id) && Objects.equals(name, guest.name)
                && Objects.equals(surname, guest.surname) && Objects.equals(birthDate, guest.birthDate)
                && Objects.equals(skiPass.getId(), guest.skiPass.getId())
                && Objects.equals(coach.getId(), guest.coach.getId())
                && Objects.equals(dateOfVisit, guest.dateOfVisit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, birthDate, skiPass, coach, dateOfVisit);
    }
}
