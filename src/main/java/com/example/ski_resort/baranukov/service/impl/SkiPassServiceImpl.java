package com.example.ski_resort.baranukov.service.impl;

import com.example.ski_resort.baranukov.dto.SkiPassDTO;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.exception.SkiPassNotFoundException;
import com.example.ski_resort.baranukov.repository.CoachRepository;
import com.example.ski_resort.baranukov.repository.GuestRepository;
import com.example.ski_resort.baranukov.repository.SkiPassRepository;
import com.example.ski_resort.baranukov.service.SkiPassService;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkiPassServiceImpl implements SkiPassService {

    private final SkiPassRepository skiPassRepository;
    private final GuestRepository guestRepository;
    private final CoachRepository coachRepository;
    private final JmsTemplate jmsProducer;

    @Override
    public List<SkiPassDTO> getAll() {
        return skiPassRepository.findAll()
                .stream()
                .map(SkiPassDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public SkiPass save(SkiPass skiPass) {
        return skiPassRepository.save(skiPass);
    }

    @Override
    public SkiPassDTO get(Long id) {
        return new SkiPassDTO(skiPassRepository.findById(id)
                .orElseThrow(() -> new SkiPassNotFoundException(id)));
    }

    @Override
    public void delete(Long id) {
        SkiPass skiPass = skiPassRepository.findById(id)
                .orElseThrow(() -> new SkiPassNotFoundException(id));

        Optional.ofNullable(skiPass.getGuest())
                .ifPresent(guest -> guestRepository.findById(
                        guest.getId()).get()
                        .setSkiPass(null));
        Optional.ofNullable(skiPass.getCoach())
                .ifPresent(coach -> coachRepository.findById(
                        coach.getId()).get()
                        .setSkiPass(null));

        skiPassRepository.delete(skiPass);
    }

    @Override
    public SkiPass update(SkiPass skiPass) {
        Optional<SkiPass> optional = skiPassRepository.findById(skiPass.getId());
        if (optional.isPresent()) {
            SkiPass updateSkiPass = optional.get();
            updateSkiPass.setId(skiPass.getId());
            updateSkiPass.setDuration(skiPass.getDuration());
            updateSkiPass.setCost(skiPass.getCost());
            return skiPassRepository.save(updateSkiPass);
        } else throw new SkiPassNotFoundException(skiPass.getId());
    }

    @Override
    public void sendListOfSkiPasses() {
        List<SkiPass> skiPasses = skiPassRepository.findAll();
        if(!skiPasses.isEmpty()){
            List<SkiPassDTO> skiPassDTOs = skiPasses.stream()
                    .map(SkiPassDTO::new)
                    .collect(Collectors.toList());
            jmsProducer.setPubSubDomain(true);
            jmsProducer.convertAndSend("topic.ski-passes", skiPassDTOs);
        }
    }
}
