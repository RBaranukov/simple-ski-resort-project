package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.dto.SkiPassDTO;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.service.SkiPassService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/ski-pass")
@RequiredArgsConstructor
public class SkiPassController {

    private final SkiPassService skiPassService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<Collection<SkiPassDTO>> showAllSkiPasses(){
        Collection<SkiPassDTO> skiPassDTOS = skiPassService.getAll();
        return ResponseEntity.ok(skiPassDTOS);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<SkiPassDTO> showSkiPass(@PathVariable final Long id){
        SkiPassDTO skiPassDTO = skiPassService.get(id);
        return ResponseEntity.ok(skiPassDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<SkiPass> addNewSkiPass(@RequestBody final SkiPass skiPass){
        skiPassService.save(skiPass);
        return new ResponseEntity<>(skiPass, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<SkiPass> updateSkiPass(@RequestBody final SkiPass skiPass){
        skiPassService.update(skiPass);
        return ResponseEntity.ok(skiPass);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSkiPass(@PathVariable final Long id){
        skiPassService.delete(id);
        return new ResponseEntity<>(String.format("SkiPass id %s was deleted", id), HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/send")
    public ResponseEntity<String> sendListOfSkiPasses(){
        skiPassService.sendListOfSkiPasses();
        return new ResponseEntity<>("Send list of Ski-Passes", HttpStatus.OK);
    }
}
