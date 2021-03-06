package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.service.CoachService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ski-resort")
@AllArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/coaches")
    public ResponseEntity<Collection<CoachDTO>> showAllCoaches(){
        Collection<CoachDTO> coachDTOS = coachService.getAll();
        return ResponseEntity.ok(coachDTOS);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/coaches/{id}")
    public ResponseEntity<CoachDTO> showCoach(@PathVariable Long id){
        CoachDTO coachDTO = coachService.get(id);
        return ResponseEntity.ok(coachDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/coaches")
    public ResponseEntity<Coach> addNewCoach(@RequestBody Coach coach){
        coachService.save(coach);
        return new ResponseEntity<>(coach, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/coaches")
    public ResponseEntity<Coach> updateCoach(@RequestBody Coach coach){
        coachService.update(coach);
        return ResponseEntity.ok(coach);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/coaches/{id}")
    public ResponseEntity<String> deleteCoachById(@PathVariable Long id){
        coachService.delete(id);
        return new ResponseEntity<>(String.format("Coach with id %s was deleted", id), HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/coaches/{id}/photo")
    public ResponseEntity<String> addPhotoToCoach(@PathVariable Long id, @RequestParam String pathNameToPhoto){
        coachService.setPhotoToCoach(id, pathNameToPhoto);
        return ResponseEntity.ok("Set photo to coach");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/send/coach/{id}")
    public ResponseEntity<String> sendCoach(@PathVariable Long id){
        coachService.sendCoach(id);
        return new ResponseEntity<>("Sent coach", HttpStatus.OK);
    }
}
