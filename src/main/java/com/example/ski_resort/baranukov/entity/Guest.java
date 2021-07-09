package com.example.ski_resort.baranukov.entity;


import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ski_pass_id")
    private SkiPass skiPass;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private Coach coach;

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
        return dateOfVisit;
    }

    public void setDateOfVisit(LocalDate dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }
}
