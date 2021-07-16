package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.exception.SkiPassNotFoundException;
import com.example.ski_resort.baranukov.repository.SkiPassRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkiPassServiceImpl implements SkiPassService{

    private final SkiPassRepository skiPassRepository;

    SkiPassServiceImpl(SkiPassRepository skiPassRepository){
        this.skiPassRepository = skiPassRepository;
    }

    @Override
    public List<SkiPass> getAllSkiPasses() {
        return skiPassRepository.findAll();
    }


    @Override
    public SkiPass save(SkiPass skiPass){
        skiPassRepository.save(skiPass);
        return skiPass;
    }

    @Override
    public SkiPass getSkiPass(Long id) {
        return skiPassRepository.findById(id).orElseThrow(() -> new SkiPassNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        skiPassRepository.deleteById(id);
    }

    @Override
    public SkiPass updateSkiPass(SkiPass skiPass){
        Optional<SkiPass> optional = skiPassRepository.findById(skiPass.getId());
        if(optional.isPresent()){
            SkiPass updateSkiPass = optional.get();
            updateSkiPass.setDuration(skiPass.getDuration());
            updateSkiPass.setCost(skiPass.getCost());
            return skiPassRepository.save(updateSkiPass);
        } else throw  new SkiPassNotFoundException(skiPass.getId());
    }
}
