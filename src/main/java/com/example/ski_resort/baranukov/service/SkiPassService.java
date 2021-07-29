package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.dto.SkiPassDTO;
import com.example.ski_resort.baranukov.entity.SkiPass;

import java.util.List;

public interface SkiPassService {

    List<SkiPassDTO> getAllSkiPasses();

    SkiPass save (SkiPass skiPass);

    SkiPassDTO getSkiPass(Long id);

    void deleteById(Long id);

    SkiPass updateSkiPass(SkiPass skiPass);
}
