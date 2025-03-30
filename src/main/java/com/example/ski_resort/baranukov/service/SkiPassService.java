package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.dto.SkiPassDTO;
import com.example.ski_resort.baranukov.entity.SkiPass;

public interface SkiPassService extends BaseCRUDService<SkiPass, SkiPassDTO> {

    void sendListOfSkiPasses();
}
