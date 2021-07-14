package com.example.ski_resort.baranukov.controller;


import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.service.CoachService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ski-resort")
public class CoachRestController {

    private final CoachService coachService;

    CoachRestController(CoachService coachService){
        this.coachService = coachService;
    }

    @GetMapping("/coaches")
    public List<Coach> showAllCoaches(){
        return coachService.getAllCoaches();
    }

    @GetMapping("/coaches/{id}")
    public Coach showCoach(@PathVariable Long id){
        return coachService.getCoach(id);
    }

    @PostMapping("/coaches")
    public Coach addNewCoach(@RequestBody Coach coach){
        coachService.save(coach);
        return coach;
    }

    @PutMapping("/coaches")
    public Coach updateCoach(@RequestBody Coach coach){
        return coachService.updateCoach(coach);
    }

    @DeleteMapping("/coaches/{id}")
    public String deleteCoachById(@PathVariable Long id){
        Coach coach = coachService.getCoach(id);
        coachService.deleteCoach(id);
        return "Coach "  + coach.getName() + " was deleted";
    }

    @PostMapping("/coaches/{id}/photo")
    public Coach addPhotoToCoach(@PathVariable Long id, @RequestParam String pathNameToPhoto){
        Coach coach = coachService.getCoach(id);
        coachService.setPhotoToCoach(coach, pathNameToPhoto);
        coachService.save(coach);
        return coach;
    }
}
