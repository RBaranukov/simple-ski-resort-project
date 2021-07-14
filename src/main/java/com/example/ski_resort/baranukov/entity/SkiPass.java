package com.example.ski_resort.baranukov.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ski_passes")
public class SkiPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="ss:mm:HH dd.MM.yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "duration")
    private LocalDateTime duration;

    @Column(name = "cost")
    private Double cost;

    @OneToOne(mappedBy = "skiPass", cascade = {CascadeType.REFRESH,CascadeType.DETACH, CascadeType.PERSIST})
    @JsonBackReference(value = "guestSkiPass")
    private Guest guest;

    @OneToOne(mappedBy = "skiPass", cascade = {CascadeType.REFRESH,CascadeType.DETACH, CascadeType.PERSIST})
    @JsonBackReference(value = "coachSkiPass" )
    private Coach coach;

    public SkiPass() {
    }

    public SkiPass(LocalDateTime duration, Double cost, Guest guest, Coach coach) {
        this.duration = duration;
        this.cost = cost;
        this.guest = guest;
        this.coach = coach;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDuration() {
        duration.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return duration;
    }

    public void setDuration(LocalDateTime duration) {
        this.duration = duration;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Guest getGuest() {
        return guest;
    }

    public Coach getCoach() {
        return coach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkiPass skiPass = (SkiPass) o;
        return Objects.equals(id, skiPass.id) && Objects.equals(duration, skiPass.duration)
                && Objects.equals(cost, skiPass.cost) && Objects.equals(guest, skiPass.guest)
                && Objects.equals(coach, skiPass.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, duration, cost, guest, coach);
    }
}
