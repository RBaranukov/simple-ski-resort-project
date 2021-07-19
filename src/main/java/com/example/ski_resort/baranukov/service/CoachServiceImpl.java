package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.exception.CoachNotFoundException;
import com.example.ski_resort.baranukov.mapper.CoachMapper;
import com.example.ski_resort.baranukov.repository.CoachRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService{

    private final @NonNull CoachRepository coachRepository;

    @Override
    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    @Override
    public Coach save(Coach coach) {
        return coachRepository.save(coach);
    }

    public Coach updateCoach(Coach coach){
        Optional<Coach> optional = coachRepository.findById(coach.getId());
        if(optional.isPresent()){
            Coach updateCoach = optional.get();
            updateCoach.setName(coach.getName());
            updateCoach.setSurname(coach.getSurname());
            updateCoach.setBirthDate(coach.getBirthDate());
            updateCoach.setGuests(coach.getGuests());
            updateCoach.setSex(coach.getSex());
            updateCoach.setCategory(coach.getCategory());
            return coachRepository.save(updateCoach);
        } else throw  new CoachNotFoundException(coach.getId());
    }

    @Override
    public Coach getCoach(Long id) {
        return coachRepository.findById(id)
                .orElseThrow(() -> new CoachNotFoundException(id));
    }

    @Override
    public void deleteCoach(Long id) {
        coachRepository.deleteById(id);
    }

    @Override
    public void setPhotoToCoach(Long id, String pathNameToPhoto) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new CoachNotFoundException(id));
        coachRepository.setPhotoToCoach(coach, pathNameToPhoto);
    }
}
