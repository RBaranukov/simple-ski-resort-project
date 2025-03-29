package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.service.CoachService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/coach")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<Collection<CoachDTO>> showAllCoaches(){
        Collection<CoachDTO> coachDTOS = coachService.getAll();
        return ResponseEntity.ok(coachDTOS);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<CoachDTO> showCoach(@PathVariable final Long id){
        CoachDTO coachDTO = coachService.get(id);
        return ResponseEntity.ok(coachDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Coach> addNewCoach(@RequestBody final Coach coach){
        coachService.save(coach);
        return new ResponseEntity<>(coach, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<Coach> updateCoach(@RequestBody final Coach coach){
        coachService.update(coach);
        return ResponseEntity.ok(coach);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoachById(@PathVariable final Long id){
        coachService.delete(id);
        return new ResponseEntity<>(String.format("Coach with id %s was deleted", id), HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/photo")
    public ResponseEntity<String> addPhotoToCoach(
            @PathVariable final Long id,
            @RequestParam final String pathNameToPhoto
    ) {
        coachService.setPhotoToCoach(id, pathNameToPhoto);
        return ResponseEntity.ok("Set photo to coach");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/send/{id}")
    public ResponseEntity<String> sendCoach(@PathVariable final Long id){
        coachService.sendCoach(id);
        return new ResponseEntity<>("Sent coach", HttpStatus.OK);
    }
}
