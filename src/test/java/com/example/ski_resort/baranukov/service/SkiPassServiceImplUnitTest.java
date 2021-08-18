package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.dto.SkiPassDTO;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.exception.SkiPassNotFoundException;
import com.example.ski_resort.baranukov.repository.SkiPassRepository;
import com.example.ski_resort.baranukov.service.impl.SkiPassServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkiPassServiceImplUnitTest {

    @Mock
    private SkiPassRepository skiPassRepository;

    @InjectMocks
    private SkiPassServiceImpl skiPassService;

    @Test
    public void getSkiPassByIdAndEqualCost() {
        SkiPass skiPass = new SkiPass(1L, LocalDateTime.now(), BigDecimal.valueOf(1756.6), null ,null );

        Mockito.when(skiPassRepository.findById(1L)).thenReturn(Optional.of(skiPass));

        SkiPassDTO skiPassDTO = skiPassService.getSkiPass(1L);

        assertEquals(skiPassDTO.getCost(), skiPass.getCost());
    }

    @Test
    public void getAllSkiPassesAndEqualListSize() {
        SkiPass skiPass1 = new SkiPass(1L, LocalDateTime.now(), BigDecimal.valueOf(1756.6), null ,null );
        SkiPass skiPass2 = new SkiPass(2L, LocalDateTime.now(), BigDecimal.valueOf(0), null ,null );
        List<SkiPass> skiPasses = Stream.of(skiPass1, skiPass2)
                .collect(Collectors.toList());

        Mockito.when(skiPassRepository.findAll()).thenReturn(skiPasses);

        List<SkiPassDTO> skiPassDTOList = skiPassService.getAllSkiPasses();

        assertEquals(skiPassDTOList.size(), skiPasses.size());
    }

    @Test
    public void saveSkiPassAndEqualCost(){
        SkiPass input = new SkiPass(1L, LocalDateTime.now(), BigDecimal.valueOf(1756.6), null ,null );
        SkiPass returned = new SkiPass(1L, LocalDateTime.now(), BigDecimal.valueOf(1756.6), null ,null );

        Mockito.when(skiPassRepository.save(input)).thenReturn(returned);

        SkiPass result = skiPassService.save(input);

        assertEquals(BigDecimal.valueOf(1756.6), result.getCost());
    }

    @Test
    public void deleteSkiPassTest(){
        SkiPass input = new SkiPass(1L, LocalDateTime.now(), BigDecimal.valueOf(1756.6), null ,null );

        Mockito.when(skiPassRepository.findById(1L)).thenReturn(Optional.of(input));
        Mockito.doNothing().when(skiPassRepository).delete(input);

        skiPassService.deleteById(input.getId());
    }

    @Test
    public void updateSkiPassTest(){
        SkiPass input = new SkiPass(1L, LocalDateTime.now(), BigDecimal.valueOf(1756.6), null ,null );

        SkiPass returned = new SkiPass(2L, LocalDateTime.now(), BigDecimal.valueOf(0), null ,null );

        Mockito.when(skiPassRepository.findById(1L)).thenReturn(Optional.of(input));
        Mockito.when(skiPassRepository.save(input)).thenReturn(returned);

        SkiPass result = skiPassService.updateSkiPass(input);

        assertEquals(BigDecimal.valueOf(0), result.getCost());
    }

    @Test(expected = SkiPassNotFoundException.class)
    public void getSkiPassByIdAndThrowSkiPassNotFoundException(){
        Mockito.doThrow(new SkiPassNotFoundException(3L)).when(skiPassRepository).findById(3L);
        skiPassService.getSkiPass(3L);
    }
}
