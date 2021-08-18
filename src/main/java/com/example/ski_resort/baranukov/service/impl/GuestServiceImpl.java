package com.example.ski_resort.baranukov.service.impl;

import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.exception.CoachNotFoundException;
import com.example.ski_resort.baranukov.exception.GuestNotFoundException;
import com.example.ski_resort.baranukov.exception.SkiPassNotFoundException;
import com.example.ski_resort.baranukov.repository.CoachRepository;
import com.example.ski_resort.baranukov.repository.GuestRepository;
import com.example.ski_resort.baranukov.entity.Guest;

import com.example.ski_resort.baranukov.repository.SkiPassRepository;
import com.example.ski_resort.baranukov.service.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final CoachRepository coachRepository;
    private final SkiPassRepository skiPassRepository;
    private final JmsTemplate jmsProducer;

    @Override
    public List<GuestDTO> getAllGuests() {
        return guestRepository.findAll()
                .stream()
                .map(GuestDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public GuestDTO getGuest(Long id) {
        return new GuestDTO(guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id)));
    }

    @Override
    public Guest save(Guest guest) {
        return guestRepository.save(guest);
    }

    @Override
    public Guest updateGuest(Guest guest) {
        Optional<Guest> optional = guestRepository.findById(guest.getId());
        if (optional.isPresent()) {
            Guest updateGuest = optional.get();
            updateGuest.setId(guest.getId());
            updateGuest.setName(guest.getName());
            updateGuest.setSurname(guest.getSurname());
            updateGuest.setBirthDate(guest.getBirthDate());
            updateGuest.setDateOfVisit(guest.getDateOfVisit());
            return guestRepository.save(updateGuest);
        } else throw new SkiPassNotFoundException(guest.getId());
    }

    @Override
    public void deleteGuest(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));
        guestRepository.delete(guest);
    }

    @Override
    public void setCoachToGuest(Long coach_id, Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));
        Coach coach = coachRepository.findById(coach_id)
                .orElseThrow(() -> new CoachNotFoundException(coach_id));
        guest.setCoach(coach);
        coach.getGuests().add(guest);
        guestRepository.save(guest);
        coachRepository.save(coach);
    }

    @Override
    public void setSkiPassToGuest(Long skiPass_id, Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));
        SkiPass skiPass = skiPassRepository.findById(skiPass_id)
                .orElseThrow(() -> new SkiPassNotFoundException(skiPass_id));
        guest.setSkiPass(skiPass);
        guestRepository.save(guest);
    }

    @Override
    public void sendListOfGuests() {
        List<Guest> guests = guestRepository.findAll();
        if(!guests.isEmpty()){
            jmsProducer.setPubSubDomain(true);
            jmsProducer.convertAndSend("topic.guests", guests);
        }
    }

    @Override
    public void send(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));
        jmsProducer.setPubSubDomain(true);
        jmsProducer.convertAndSend("topic.guests", guest);
    }
}