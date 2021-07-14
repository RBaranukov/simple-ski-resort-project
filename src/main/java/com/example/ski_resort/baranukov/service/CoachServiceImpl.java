package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.exception.CoachNotFoundException;
import com.example.ski_resort.baranukov.exception.SkiPassNotFoundException;
import com.example.ski_resort.baranukov.repository.CoachRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachServiceImpl implements CoachService{

    private final CoachRepository coachRepository;

    CoachServiceImpl(CoachRepository coachRepository){
        this.coachRepository = coachRepository;
    }

    @Override
    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    @Override
    public Coach save(Coach coach) {
        coachRepository.save(coach);
        return coach;
    }

    public Coach updateCoach(Coach coach){
        Optional<Coach> optional = coachRepository.findById(coach.getId());
        if(optional.isPresent()){
            Coach updateCoach = optional.get();
            updateCoach.setName(coach.getName());
            updateCoach.setSurname(coach.getSurname());
            updateCoach.setBirthDate(coach.getBirthDate());
            updateCoach.setGuestList(coach.getGuestList());
            updateCoach.setSex(coach.getSex());
            updateCoach.setCategory(coach.getCategory());
            return coachRepository.save(updateCoach);
        } else throw  new SkiPassNotFoundException(coach.getId());
    }

    @Override
    public Coach getCoach(Long id) {
        return coachRepository.findById(id).orElseThrow(() -> new CoachNotFoundException(id));
    }

    @Override
    public void deleteCoach(Long id) {
        coachRepository.deleteById(id);
    }

    @Override
    public void setPhotoToCoach(Coach coach, String pathNameToPhoto) {
        coachRepository.setPhotoToCoach(coach, pathNameToPhoto);
    }
}
