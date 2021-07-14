package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.service.SkiPassService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ski-resort")
public class SkiPassRestController {

    private final SkiPassService skiPassService;

    SkiPassRestController(SkiPassService skiPassService){
        this.skiPassService = skiPassService;
    }

    @GetMapping("/ski-passes")
    public List<SkiPass> showAllSkiPasses(){
        return skiPassService.getAllSkiPasses();
    }

    @GetMapping("/ski_passes/{id}")
    public SkiPass showSkiPass(@PathVariable Long id){
        return skiPassService.getSkiPass(id);
    }

    @PostMapping("/ski-passes")
    public SkiPass addNewSkiPass(@RequestBody SkiPass skiPass){
        skiPassService.save(skiPass);
        return skiPass;
    }

    @PutMapping("/ski-passes/")
    public SkiPass updateSkiPass(@RequestBody SkiPass skiPass){
        return skiPassService.updateSkiPass(skiPass);
    }

    @DeleteMapping("/ski-passes/{id}")
    public String deleteSkiPass(@PathVariable Long id){
        SkiPass skiPass = skiPassService.getSkiPass(id);
        skiPassService.deleteById(id);
        return "SkiPass №" + skiPass.getId() + " was deleted";
    }
}
