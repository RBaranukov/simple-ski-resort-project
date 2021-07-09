package com.example.ski_resort.baranukov.repository;

import com.example.ski_resort.baranukov.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long>, CustomizedSetSkiPassAndCoach {

}
