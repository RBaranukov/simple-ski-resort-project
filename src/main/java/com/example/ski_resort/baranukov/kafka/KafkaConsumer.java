package com.example.ski_resort.baranukov.kafka;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.dto.SkiPassDTO;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.entity.User;
import com.example.ski_resort.baranukov.repository.SkiPassRepository;
import com.example.ski_resort.baranukov.service.GuestService;
import com.example.ski_resort.baranukov.service.SkiPassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final GuestService guestService;
    private final SkiPassService skiPassService;
    private final SkiPassRepository skiPassRepository;


    @KafkaListener(topics = "coach")
    public void receivedCoachFromQueue(final CoachDTO coach) {
        log.info("Message received from queue: " + coach);
    }

    @KafkaListener(topics = "guests")
    public void receivedListOfGuestsFromTopic(final List<GuestDTO> guests) {
        log.info("Message received from topic: " + guests);
        
    }

    @KafkaListener(topics = "guest")
    public void receivedGuestFromTopic(final GuestDTO guest) {
        log.info("Message received from topic: " + guest);
    }

    @KafkaListener(topics = "skiPass")
    public void receivedListOfSkiPassesFromTopic(final List<SkiPassDTO> skiPasses) {
        log.info("Message received from topic: " + skiPasses);
    }

    @KafkaListener(topics = "user")
    public void receivedUserFromTopic(final User user) {
        log.info("Message received from queue: " + user);
    }

    @KafkaListener(topics = "passwordExpiration")
    public void receivedGuestDTOFromTopic(final GuestDTO guestDTO) {
        log.info("Message received from topic: " + guestDTO);
        final var skiPassDuration = LocalDateTime.of(LocalDate.now(), LocalTime.NOON).plusDays(7);
        Optional.ofNullable(guestService.get(guestDTO.getId()))
                .ifPresent(guest-> {
                    SkiPass skiPass = skiPassRepository.findById(guest.getSkiPassId()).get();
                    skiPass.setDuration(skiPassDuration);
                    skiPassService.update(skiPass);
                });
        log.info("SkiPass was updated");
    }
}
