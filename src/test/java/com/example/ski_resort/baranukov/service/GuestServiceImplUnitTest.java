package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.exception.GuestNotFoundException;
import com.example.ski_resort.baranukov.repository.GuestRepository;
import com.example.ski_resort.baranukov.service.impl.GuestServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuestServiceImplUnitTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestServiceImpl guestService;

    @Test
    public void getGuestByIdAndEqualName() {
        Guest guest = new Guest("Roma", "Atnik", LocalDate.now(), null, null,  LocalDate.now());
        guest.setId(1L);
        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));

        GuestDTO guestDTO = guestService.get(1L);

        assertEquals(guestDTO.getName(), guest.getName());
    }

    @Test
    public void getAllGuestsAndEqualListSize() {
        Guest guest1 = new Guest("Roma", "Atnik", LocalDate.now(), null, null,  LocalDate.now());
        Guest guest2 = new Guest("Igor", "Rambo", LocalDate.now(), null, null,  LocalDate.now());
        List<Guest> guests = Stream.of(guest1, guest2)
                .collect(Collectors.toList());

        Mockito.when(guestRepository.findAll()).thenReturn(guests);

        List<GuestDTO> guestDTOList = guestService.getAll();

        assertEquals(guestDTOList.size(), guests.size());
    }

    @Test
    public void saveGuestAndEqualName(){
        Guest input = new Guest("Roma", "Atnik", LocalDate.now(), null, null,  LocalDate.now());
        Guest returned = new Guest("Roma", "Atnik", LocalDate.now(), null, null,  LocalDate.now());

        Mockito.when(guestRepository.save(input)).thenReturn(returned);

        Guest result = guestService.save(input);

        assertEquals("Atnik", result.getSurname());
    }

    @Test
    public void deleteGuestTest(){
        Guest input = new Guest("Roma", "Atnik", LocalDate.now(), null, null,  LocalDate.now());
        input.setId(1L);

        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.of(input));
        Mockito.doNothing().when(guestRepository).delete(input);

        guestService.delete(input.getId());
    }

    @Test
    public void updateGuestTest(){
        Guest input = new Guest("Roma", "Atnik", LocalDate.now(), null, null,  LocalDate.now());
        input.setId(1L);

        Guest returned = new Guest("Igor", "Rambo", LocalDate.now(), null, null,  LocalDate.now());
        returned.setId(1L);

        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.of(input));
        Mockito.when(guestRepository.save(input)).thenReturn(returned);

        Guest result = guestService.update(input);

        assertEquals("Igor", result.getName());
    }

    @Test(expected = GuestNotFoundException.class)
    public void getGuestByIdAndThrowGuestNotFoundException(){
        Mockito.doThrow(new GuestNotFoundException(3L)).when(guestRepository).findById(3L);
        guestService.get(3L);
    }
}