package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.dto.SkiPassDTO;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.service.SkiPassService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ski-resort")
@RequiredArgsConstructor
public class SkiPassRestController {

    private final @NonNull SkiPassService skiPassService;

    @GetMapping("/ski-passes")
    public ResponseEntity<List<SkiPassDTO>> showAllSkiPasses(){
        List<SkiPassDTO> skiPassDTOS = skiPassService.getAllSkiPasses();
        return ResponseEntity.ok(skiPassDTOS);
    }

    @GetMapping("/ski-passes/{id}")
    public ResponseEntity<SkiPassDTO> showSkiPass(@PathVariable Long id){
        SkiPassDTO skiPassDTO = skiPassService.getSkiPass(id);
        return ResponseEntity.ok(skiPassDTO);
    }

    @PostMapping("/ski-passes")
    public ResponseEntity<SkiPass> addNewSkiPass(@RequestBody SkiPass skiPass){
        skiPassService.save(skiPass);
        return new ResponseEntity<>(skiPass, HttpStatus.CREATED);
    }

    @PutMapping("/ski-passes/")
    public ResponseEntity<SkiPass> updateSkiPass(@RequestBody SkiPass skiPass){
        skiPassService.updateSkiPass(skiPass);
        return ResponseEntity.ok(skiPass);
    }

    @DeleteMapping("/ski-passes/{id}")
    public ResponseEntity<String> deleteSkiPass(@PathVariable Long id){
        skiPassService.deleteById(id);
        return new ResponseEntity<>(String.format("SkiPass id %s was deleted", id), HttpStatus.NO_CONTENT);
    }
}
