package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.mapper.GuestMapper;
import com.example.ski_resort.baranukov.service.GuestService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ski-resort")
@RequiredArgsConstructor
public class GuestRestController {

    private final @NonNull GuestService guestService;
    private final @NonNull GuestMapper guestMapper;

    @GetMapping("/guests")
    public List<GuestDTO> showAllGuests(){
        return guestService.getAllGuests()
                .stream()
                .map(guestMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/guests/{id}")
    public GuestDTO showGuest(@PathVariable Long id){
        Guest guest = guestService.getGuest(id);
        return guestMapper.INSTANCE.toDTO(guest);
    }

    @PostMapping("/guests")
    public Guest addNewGuest(@RequestBody Guest guest){
        return guestService.save(guest);
    }

    @PutMapping("/guests")
    public Guest updateGuest(@RequestBody Guest guest){
        return guestService.updateGuest(guest);
    }

    @DeleteMapping("/guests/{id}")
    public void deleteGuest(@PathVariable Long id){
        guestService.deleteGuest(id);
    }

    @PostMapping("/guests/{id}/{coach_id}")
    public void addCoachToGuest(@PathVariable Long coach_id, @PathVariable Long id){
        guestService.setCoachToGuest(coach_id, id);
    }

    @PostMapping("/guests/{id}/{ski_pass_id}")
    public void addSkiPassToGuest(@PathVariable Long ski_pass_id, @PathVariable Long id){
        guestService.setSkiPassToGuest(ski_pass_id, id);
    }
}