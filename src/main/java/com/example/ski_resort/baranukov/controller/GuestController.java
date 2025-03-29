package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.service.GuestService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<Collection<GuestDTO>> showAllGuests() {
        Collection<GuestDTO> guestDTOS = guestService.getAll();
        return ResponseEntity.ok(guestDTOS);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> showGuest(@PathVariable final Long id) {
        GuestDTO guestDTO = guestService.get(id);
        return ResponseEntity.ok(guestDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<Guest> addNewGuest(@RequestBody final Guest guest) {
        guestService.save(guest);
        return new ResponseEntity<>(guest, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping
    public ResponseEntity<Guest> updateGuest(@RequestBody final Guest guest) {
        guestService.update(guest);
        return ResponseEntity.ok(guest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGuest(@PathVariable final Long id) {
        guestService.delete(id);
        return new ResponseEntity<>(String.format("Guest with id %s was deleted", id), HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping("/{id}/coach/{coach_id}")
    public ResponseEntity<String> addCoachToGuest(
            @PathVariable final Long coach_id,
            @PathVariable final Long id
    ) {
        guestService.setCoachToGuest(coach_id, id);
        return ResponseEntity.ok(String.format("Set coach with id %s to guest with id %s", coach_id, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping("/{id}/skipass/{ski_pass_id}")
    public ResponseEntity<String> addSkiPassToGuest(
            @PathVariable final Long ski_pass_id,
            @PathVariable final Long id
    ) {
        guestService.setSkiPassToGuest(ski_pass_id, id);
        return ResponseEntity.ok(String.format("Set SkiPass with id %s to guest with id %s", ski_pass_id, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/send")
    public ResponseEntity<String> sendListOfGuests() {
        guestService.sendListOfGuests();
        return new ResponseEntity<>("Send list of guests", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/send/{id}")
    public ResponseEntity<String> sendGuest(@PathVariable final Long id) {
        guestService.sendGuest(id);
        return new ResponseEntity<>("Send guest", HttpStatus.OK);
    }
}