package com.example.ski_resort.baranukov.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @Column(name = "active")
    private boolean isActive;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
