package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.exception.GuestNotFoundException;
import com.example.ski_resort.baranukov.exception.SkiPassNotFoundException;
import com.example.ski_resort.baranukov.repository.GuestRepository;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.entity.SkiPass;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService{

    private final GuestRepository guestRepository;

    GuestServiceImpl(GuestRepository guestRepository){
        this.guestRepository = guestRepository;
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest getGuest(Long id) {
        return guestRepository.findById(id).orElseThrow(() -> new GuestNotFoundException(id));
    }

    @Override
    public Guest save(Guest guest) {
        guestRepository.save(guest);
        return guest;
    }

    @Override
    public Guest updateGuest(Guest guest){
        Optional<Guest> optional = guestRepository.findById(guest.getId());
        if(optional.isPresent()){
            Guest updateGuest = optional.get();
            updateGuest.setName(guest.getName());
            updateGuest.setSurname(guest.getSurname());
            updateGuest.setBirthDate(guest.getBirthDate());
            updateGuest.setDateOfVisit(guest.getDateOfVisit());
            return guestRepository.save(updateGuest);
        } else throw  new SkiPassNotFoundException(guest.getId());
    }

    @Override
    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    @Override
    public void setCoach(Coach coach, Long id) {
        //coachRepository.findById(coach.getId); Use Optional for null-value Coach
        guestRepository.setCoach(coach, id);
    }

    @Override
    public void setSkiPass(SkiPass skiPass, Long id) {
        //skiPassRepository.findById(skiPass.getId()); Use Optional for null-value SkiPass
        guestRepository.setSkiPass(skiPass, id);
    }
}
