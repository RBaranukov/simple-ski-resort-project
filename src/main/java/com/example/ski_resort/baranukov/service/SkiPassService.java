package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.entity.SkiPass;

import java.util.List;

public interface SkiPassService {

    List<SkiPass> getAllSkiPasses();

    SkiPass save (SkiPass skiPass);

    SkiPass getSkiPass(Long id);

    void deleteById(Long id);

    SkiPass updateSkiPass(SkiPass skiPass);
}
