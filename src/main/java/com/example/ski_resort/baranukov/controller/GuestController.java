package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.service.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ski-resort")
@AllArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/guests")
    public ResponseEntity<Collection<GuestDTO>> showAllGuests() {
        Collection<GuestDTO> guestDTOS = guestService.getAll();
        return ResponseEntity.ok(guestDTOS);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/guests/{id}")
    public ResponseEntity<GuestDTO> showGuest(@PathVariable Long id) {
        GuestDTO guestDTO = guestService.get(id);
        return ResponseEntity.ok(guestDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/guests")
    public ResponseEntity<Guest> addNewGuest(@RequestBody Guest guest) {
        guestService.save(guest);
        return new ResponseEntity<>(guest, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping("/guests")
    public ResponseEntity<Guest> updateGuest(@RequestBody Guest guest) {
        guestService.update(guest);
        return ResponseEntity.ok(guest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @DeleteMapping("/guests/{id}")
    public ResponseEntity<String> deleteGuest(@PathVariable Long id) {
        guestService.delete(id);
        return new ResponseEntity<>(String.format("Guest with id %s was deleted", id), HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping("/guests/{id}/coach-{coach_id}")
    public ResponseEntity<String> addCoachToGuest(@PathVariable Long coach_id, @PathVariable Long id) {
        guestService.setCoachToGuest(coach_id, id);
        return ResponseEntity.ok(String.format("Set coach with id %s to guest with id %s", coach_id, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping("/guests/{id}/skipass-{ski_pass_id}")
    public ResponseEntity<String> addSkiPassToGuest(@PathVariable Long ski_pass_id, @PathVariable Long id) {
        guestService.setSkiPassToGuest(ski_pass_id, id);
        return ResponseEntity.ok(String.format("Set SkiPass with id %s to guest with id %s", ski_pass_id, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/send/guests")
    public ResponseEntity<String> sendListOfGuests(){
        guestService.sendListOfGuests();
        return new ResponseEntity<>("Send list of guests", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/send/guest/{id}")
    public ResponseEntity<String> sendGuest(@PathVariable Long id){
        guestService.sendGuest(id);
        return new ResponseEntity<>("Send guest", HttpStatus.OK);
    }
}