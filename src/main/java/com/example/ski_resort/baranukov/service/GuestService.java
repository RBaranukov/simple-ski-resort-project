package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.SkiPass;

import java.util.List;

public interface GuestService {

    List<Guest> getAllGuests();

    Guest save(Guest guest);

    Guest updateGuest(Guest guest);

    Guest getGuest(Long id);

    void deleteGuest(Long id);

    void setCoach(Coach coach, Long id);

    void setSkiPass(SkiPass skiPass, Long id);
}
