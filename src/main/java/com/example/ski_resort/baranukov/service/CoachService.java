package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.entity.Coach;

public interface  CoachService extends BaseCRUDService<Coach, CoachDTO> {

    void setPhotoToCoach(Long id, String pathNameToPhoto);

    void sendCoach(Long id);
}
