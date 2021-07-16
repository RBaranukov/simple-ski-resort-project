package com.example.ski_resort.baranukov.repository;

import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.repository.customized.CustomizedSetSkiPassAndCoach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long>, CustomizedSetSkiPassAndCoach {

}
