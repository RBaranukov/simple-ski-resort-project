package com.example.ski_resort.baranukov.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ski_passes")
public class SkiPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "duration")
    private LocalDateTime duration;

    @Column(name = "cost")
    private Double cost;

    @OneToOne(mappedBy = "skiPass", cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Guest guest;

    @OneToOne(mappedBy = "skiPass", cascade = {CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
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

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }


}
