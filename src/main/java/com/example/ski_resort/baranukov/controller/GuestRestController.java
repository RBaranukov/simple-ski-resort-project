package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.service.GuestService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ski-resort")
@RequiredArgsConstructor
public class GuestRestController {

    private final @NonNull GuestService guestService;

    @GetMapping("/guests")
    public ResponseEntity<List<GuestDTO>> showAllGuests() {
        List<GuestDTO> guestDTOS = guestService.getAllGuests();
        return ResponseEntity.ok(guestDTOS);
    }

    @GetMapping("/guests/{id}")
    public ResponseEntity<GuestDTO> showGuest(@PathVariable Long id) {
        GuestDTO guestDTO = guestService.getGuest(id);
        return ResponseEntity.ok(guestDTO);
    }

    @PostMapping("/guests")
    public ResponseEntity<Guest> addNewGuest(@RequestBody Guest guest) {
        guestService.save(guest);
        return new ResponseEntity<>(guest, HttpStatus.CREATED);
    }

    @PutMapping("/guests")
    public ResponseEntity<Guest> updateGuest(@RequestBody Guest guest) {
        guestService.updateGuest(guest);
        return ResponseEntity.ok(guest);
    }

    @DeleteMapping("/guests/{id}")
    public ResponseEntity<String> deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return new ResponseEntity<>(String.format("Guest with id %s was deleted", id), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/guests/{id}/{coach_id}")
    public ResponseEntity<String> addCoachToGuest(@PathVariable Long coach_id, @PathVariable Long id) {
        guestService.setCoachToGuest(coach_id, id);
        return ResponseEntity.ok(String.format("Set coach with id %s to guest with id %s", coach_id, id));
    }

    @PostMapping("/guests/{id}/{ski_pass_id}")
    public ResponseEntity<String> addSkiPassToGuest(@PathVariable Long ski_pass_id, @PathVariable Long id) {
        guestService.setSkiPassToGuest(ski_pass_id, id);
        return ResponseEntity.ok(String.format("Set SkiPass with id %s to guest with id %s", ski_pass_id, id));
    }
}