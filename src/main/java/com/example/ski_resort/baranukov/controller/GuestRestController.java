package com.example.ski_resort.baranukov.controller;


import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.service.GuestService;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.SkiPass;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ski-resort")
public class GuestRestController {

    private final GuestService guestService;

    GuestRestController(GuestService guestService){
        this.guestService = guestService;
    }

    @GetMapping("/guests")
    public List<Guest> showAllGuests(){
        return guestService.getAllGuests();
    }

    @GetMapping("/guests/{id}")
    public Guest showGuest(@PathVariable Long id){
        return guestService.getGuest(id);
    }

    @PostMapping("/guests")
    public Guest addNewGuest(@RequestBody Guest guest){
        guestService.save(guest);
        return guest;
    }

    @PutMapping("/guests")
    public Guest updateGuest(@RequestBody Guest guest){
        return guestService.updateGuest(guest);
    }

    @DeleteMapping("/guests/{id}")
    public String deleteGuest(@PathVariable Long id){
        Guest guest = guestService.getGuest(id);
        guestService.deleteGuest(id);
        return "Guest " + guest.getName() + " was deleted";
    }

    @PostMapping("/guests/{id}/coach")
    public Guest addCoachToGuest(@RequestBody Coach coach, @PathVariable Long id){
        Guest guest = guestService.getGuest(id);
        guestService.setCoach(coach, id);
        return guest;
    }

    @PostMapping("/guests/{id}/ski-pass")
    public Guest addSkiPassToGuest(@RequestBody SkiPass skiPass, @PathVariable Long id){
        Guest guest = guestService.getGuest(id);
        guestService.setSkiPass(skiPass, id);
        return guest;
    }
}
