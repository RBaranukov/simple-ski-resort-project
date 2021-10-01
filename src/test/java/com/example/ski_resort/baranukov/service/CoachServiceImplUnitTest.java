package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.exception.CoachNotFoundException;
import com.example.ski_resort.baranukov.repository.CoachRepository;
import com.example.ski_resort.baranukov.service.impl.CoachServiceImpl;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoachServiceImplUnitTest {

    @Mock
    private CoachRepository coachRepository;

    @InjectMocks
    private CoachServiceImpl coachService;

    @Test
    public void getCoachByIdAndEqualName() {
        Coach coach1 = new Coach("Roma", "Atnik", "Ski", 'm', LocalDate.now(), new byte[]{1, 3, 4, 5}, null, null);
        coach1.setId(1L);
        Mockito.when(coachRepository.findById(1L)).thenReturn(Optional.of(coach1));

        CoachDTO coachDTO = coachService.get(1L);

        assertEquals(coachDTO.getName(), coach1.getName());
    }

    @Test
    public void getAllCoachesAndEqualListSize() {
        Coach coach1 = new Coach("Roma", "Atnik", "Ski", 'm', LocalDate.now(), new byte[]{1, 3, 4, 5}, null, null);
        coach1.setId(1L);
        Coach coach2 = new Coach("Andrey", "Grubj", "Snowboard", 'm', LocalDate.now(), new byte[]{1, 3, 4, 5}, null, null);
        coach2.setId(2L);
        List<Coach> coaches = Stream.of(coach1, coach2)
                .collect(Collectors.toList());

        Mockito.when(coachRepository.findAll()).thenReturn(coaches);

        Collection<CoachDTO> coachDTOList = coachService.getAll();

        assertEquals(coachDTOList.size(), coaches.size());
    }

    @Test
    public void saveCoachAndEqualName(){
        Coach input = new Coach("Roma", "Atnik", "Ski", 'm', LocalDate.now(), new byte[]{1, 3, 4, 5}, null, null);
        Coach returned = new Coach("Roma", "Atnik", "Ski", 'm', LocalDate.now(), new byte[]{1, 3, 4, 5}, null, null);

        Mockito.when(coachRepository.save(input)).thenReturn(returned);

        Coach result = coachService.save(input);

        assertEquals("Atnik", result.getSurname());
    }

    @Test
    public void deleteCoachTest(){
        Coach input = new Coach("Roma", "Atnik", "Ski", 'm', LocalDate.now(), new byte[]{1, 3, 4, 5}, null, null);
        input.setId(1L);

        Mockito.when(coachRepository.findById(1L)).thenReturn(Optional.of(input));
        Mockito.doNothing().when(coachRepository).delete(input);

        coachService.delete(input.getId());
    }

    @Test
    public void updateCoachTest(){
        Coach input = new Coach("Roma", "Atnik", "Ski", 'm', LocalDate.now(), new byte[]{1, 3, 4, 5}, null, null);
        input.setId(1L);

        Coach returned = new Coach("Andrey", "Grubj", "Snowboard", 'm', LocalDate.now(), new byte[]{1, 3, 4, 5}, null, null);
        returned.setId(1L);

        Mockito.when(coachRepository.findById(1L)).thenReturn(Optional.of(input));
        Mockito.when(coachRepository.save(input)).thenReturn(returned);

        Coach result = coachService.update(input);

        assertEquals("Andrey", result.getName());
    }

    @Test(expected = CoachNotFoundException.class)
    public void getCoachByIdAndThrowCoachNotFoundException(){
        Mockito.doThrow(new CoachNotFoundException(3L)).when(coachRepository).findById(3L);
        coachService.get(3L);
    }
}