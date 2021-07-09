package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.repository.GuestRepository;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.entity.SkiPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService{

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest getGuest(Long id) {
        Guest guest = null;
        Optional<Guest> optional = guestRepository.findById(id);
        if(optional.isPresent()){
            guest = optional.get();
        }
        return guest;
    }

    @Override
    public void save(Guest guest) {
        guestRepository.save(guest);
    }

    @Override
    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    @Override
    public void setCoach(Coach coach, Long id) {
        guestRepository.setCoach(coach, id);
    }

    @Override
    public void setSkiPass(SkiPass skiPass, Long id) {
        guestRepository.setSkiPass(skiPass, id);
    }
}
