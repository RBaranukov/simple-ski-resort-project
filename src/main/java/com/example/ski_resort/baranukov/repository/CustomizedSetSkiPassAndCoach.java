package com.example.ski_resort.baranukov.repository;

import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.SkiPass;

public interface CustomizedSetSkiPassAndCoach{

    void setSkiPass(SkiPass skiPass, Long id);

    void setCoach(Coach coach, Long id);
}

