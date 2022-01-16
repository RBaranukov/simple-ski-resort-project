package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.entity.Guest;

public interface GuestService extends BaseCRUDService<Guest, GuestDTO> {

    void setCoachToGuest(Long coach_id, Long id);

    void setSkiPassToGuest(Long skiPass_id, Long id);

    void sendListOfGuests();

    void sendGuest(Long id);

    void sendAndProlongSkiPass(Long id);
}
