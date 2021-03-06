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
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final CoachRepository coachRepository;
    private final SkiPassRepository skiPassRepository;
    private final JmsTemplate jmsProducer;

    @Override
    public List<GuestDTO> getAll() {
        log.info("Get all guests");
        return guestRepository.findAll()
                .stream()
                .map(GuestDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public GuestDTO get(Long id) {
        log.info("Get guest");
        return new GuestDTO(guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id)));
    }

    @Override
    public Guest save(Guest guest) {
        log.info("Save guest");
        return guestRepository.save(guest);
    }

    @Override
    public Guest update(Guest guest) {
        log.info("Update guest");
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
    public void delete(Long id) {
        log.info("Delete guest");
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));
        guestRepository.delete(guest);
    }

    @Override
    public void setCoachToGuest(Long coach_id, Long id) {
        log.info("Set coach to guest");
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
        log.info("Set Skipass to guest");
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));
        SkiPass skiPass = skiPassRepository.findById(skiPass_id)
                .orElseThrow(() -> new SkiPassNotFoundException(skiPass_id));
        guest.setSkiPass(skiPass);
        guestRepository.save(guest);
    }

    @Override
    public void sendListOfGuests() {
        log.info("Send list of guests");
        List<Guest> guests = guestRepository.findAll();
        if(!guests.isEmpty()){
            List<GuestDTO> guestDTOS = guests.stream()
                    .map(GuestDTO::new)
                    .collect(Collectors.toList());
            jmsProducer.setPubSubDomain(true);
            jmsProducer.convertAndSend("topic.guests", guestDTOS);
        }
    }

    @Override
    public void sendGuest(Long id) {
        log.info("Send guest");
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));
        GuestDTO guestDTO = new GuestDTO(guest);
        jmsProducer.setPubSubDomain(true);
        jmsProducer.convertAndSend("topic.guest", guestDTO);
    }

    @Override
    public void sendAndProlongSkiPass(Long id) {
        log.info("Send and prolong guest Skipass");
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));
        GuestDTO guestDTO = new GuestDTO(guest);
        jmsProducer.setPubSubDomain(true);
        jmsProducer.convertAndSend("topic.guestProlongation", guestDTO);
    }
}