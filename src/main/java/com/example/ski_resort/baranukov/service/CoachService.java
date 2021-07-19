package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.entity.Coach;

import java.util.List;

public interface CoachService {

    List<Coach> getAllCoaches();

    Coach getCoach(Long id);

    Coach save(Coach coach);

    Coach updateCoach(Coach coach);

    void deleteCoach(Long id);

    void setPhotoToCoach(Long id, String pathNameToPhoto);
}
