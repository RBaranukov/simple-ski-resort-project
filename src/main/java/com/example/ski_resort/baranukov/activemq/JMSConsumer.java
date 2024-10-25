package com.example.ski_resort.baranukov.activemq;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.dto.SkiPassDTO;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.entity.User;
import com.example.ski_resort.baranukov.repository.SkiPassRepository;
import com.example.ski_resort.baranukov.service.GuestService;
import com.example.ski_resort.baranukov.service.SkiPassService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JMSConsumer {

    private final GuestService guestService;
    private final SkiPassService skiPassService;
    private final SkiPassRepository skiPassRepository;

    private static final String MESSAGE = "Message received from queue: ";

    @JmsListener(destination = "queue.coach", containerFactory = "queueFactory")
    public void receivedCoachFromQueue(CoachDTO coach) {
        log.info("{}{}", MESSAGE, coach);
    }

    @JmsListener(destination = "topic.guests", containerFactory = "topicFactory")
    public void receivedListOfGuestsFromTopic(List<GuestDTO> guests) {
        log.info("{}{}", MESSAGE, guests);
    }

    @JmsListener(destination = "topic.guest", containerFactory = "topicFactory")
    public void receivedGuestFromTopic(GuestDTO guest) {
        log.info("{}{}", MESSAGE, guest);
    }

    @JmsListener(destination = "topic.ski-passes", containerFactory = "topicFactory")
    public void receivedListOfSkiPassesFromTopic(List<SkiPassDTO> skiPasses) {
        log.info("{}{}", MESSAGE, skiPasses);
    }

    @JmsListener(destination = "queue.user", containerFactory = "queueFactory")
    public void receivedUserFromTopic(User user) {
        log.info("{}{}", MESSAGE, user);
    }

    @JmsListener(destination = "topic.guestProlongation", containerFactory = "topicFactory")
    public void receivedGuestDTOFromTopic(GuestDTO guestDTO) {
        log.info("{}{}", MESSAGE, guestDTO);
        LocalDateTime skiPassDuration = LocalDateTime.of(LocalDate.now(), LocalTime.NOON).plusDays(7);
        Optional.ofNullable(guestService.get(guestDTO.getId()))
                .flatMap(guest -> skiPassRepository.findById(guest.getSkiPassId()))
                .ifPresent(skiPass -> {
                    skiPass.setDuration(skiPassDuration);
                    skiPassService.update(skiPass);
                });
        log.info("SkiPass was updated");
    }
}
