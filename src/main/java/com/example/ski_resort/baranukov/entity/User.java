package com.example.ski_resort.baranukov.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "active")
    boolean isActive;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    Role role;
}
