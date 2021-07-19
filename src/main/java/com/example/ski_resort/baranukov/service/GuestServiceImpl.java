package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.exception.GuestNotFoundException;
import com.example.ski_resort.baranukov.exception.SkiPassNotFoundException;
import com.example.ski_resort.baranukov.repository.GuestRepository;
import com.example.ski_resort.baranukov.entity.Guest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final @NonNull GuestRepository guestRepository;

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest getGuest(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));
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
            updateGuest.setName(guest.getName());
            updateGuest.setSurname(guest.getSurname());
            updateGuest.setBirthDate(guest.getBirthDate());
            updateGuest.setDateOfVisit(guest.getDateOfVisit());
            return guestRepository.save(updateGuest);
        } else throw new SkiPassNotFoundException(guest.getId());
    }

    @Override
    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    @Override
    public void setCoachToGuest(Long coach_id, Long id) {
        guestRepository.setCoachToGuest(coach_id, id);
    }

    @Override
    public void setSkiPassToGuest(Long skiPass_id, Long id) {
        guestRepository.setSkiPassToGuest(skiPass_id, id);
    }
}