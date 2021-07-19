package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.mapper.CoachMapper;
import com.example.ski_resort.baranukov.service.CoachService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ski-resort")
@RequiredArgsConstructor
public class CoachRestController {

    private final @NonNull CoachService coachService;
    private final @NonNull CoachMapper coachMapper;

    @GetMapping("/coaches")
    public List<CoachDTO> showAllCoaches(){
        return coachService.getAllCoaches()
                .stream()
                .map(coachMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/coaches/{id}")
    public CoachDTO showCoach(@PathVariable Long id){
        Coach coach = coachService.getCoach(id);
        return coachMapper.INSTANCE.toDTO(coach);
    }

    @PostMapping("/coaches")
    public Coach addNewCoach(@RequestBody Coach coach){
        return coachService.save(coach);
    }

    @PutMapping("/coaches")
    public Coach updateCoach(@RequestBody Coach coach){
        return coachService.updateCoach(coach);
    }

    @DeleteMapping("/coaches/{id}")
    public void deleteCoachById(@PathVariable Long id){
        coachService.deleteCoach(id);
    }

    @PostMapping("/coaches/{id}/photo")
    public void addPhotoToCoach(@PathVariable Long id, @RequestParam String pathNameToPhoto){
        coachService.setPhotoToCoach(id, pathNameToPhoto);
    }
}
