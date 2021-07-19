package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.dto.SkiPassDTO;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.mapper.SkiPassMapper;
import com.example.ski_resort.baranukov.service.SkiPassService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ski-resort")
@RequiredArgsConstructor
public class SkiPassRestController {

    private final @NonNull SkiPassService skiPassService;
    private final @NonNull SkiPassMapper skiPassMapper;

    @GetMapping("/ski-passes")
    public List<SkiPassDTO> showAllSkiPasses(){
        return skiPassService.getAllSkiPasses()
                .stream()
                .map(skiPassMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/ski-passes/{id}")
    public SkiPassDTO showSkiPass(@PathVariable Long id){
        SkiPass skiPass = skiPassService.getSkiPass(id);
        return skiPassMapper.INSTANCE.toDTO(skiPass);
    }

    @PostMapping("/ski-passes")
    public SkiPass addNewSkiPass(@RequestBody SkiPass skiPass){
        return skiPassService.save(skiPass);
    }

    @PutMapping("/ski-passes/")
    public SkiPass updateSkiPass(@RequestBody SkiPass skiPass){
        return skiPassService.updateSkiPass(skiPass);
    }

    @DeleteMapping("/ski-passes/{id}")
    public void deleteSkiPass(@PathVariable Long id){
        skiPassService.deleteById(id);
    }
}
