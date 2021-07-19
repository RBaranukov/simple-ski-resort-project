package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.entity.Guest;

import java.util.List;

public interface GuestService {

    List<Guest> getAllGuests();

    Guest save(Guest guest);

    Guest updateGuest(Guest guest);

    Guest getGuest(Long id);

    void deleteGuest(Long id);

    void setCoachToGuest(Long coach_id, Long id);

    void setSkiPassToGuest(Long skiPass_id, Long id);
}
