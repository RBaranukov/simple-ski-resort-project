package com.example.ski_resort.baranukov.activemq;

import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.entity.User;
import com.example.ski_resort.baranukov.repository.GuestRepository;
import com.example.ski_resort.baranukov.repository.SkiPassRepository;
import com.example.ski_resort.baranukov.service.CoachService;
import com.example.ski_resort.baranukov.service.GuestService;
import com.example.ski_resort.baranukov.service.SkiPassService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JMSConsumer {

    private final static Logger logger = LoggerFactory.getLogger(JMSConsumer.class);
    private final @NonNull GuestService guestService;
    private final @NonNull CoachService coachService;
    private final @NonNull SkiPassService skiPassService;
    private final @NonNull GuestRepository guestRepository;
    private final @NonNull SkiPassRepository skiPassRepository;

    @JmsListener(destination = "queue.coach", containerFactory = "queueFactory")
    public void receivedCoachFromQueue(Coach coach) {
        logger.info("Message received from queue: " + coach);
    }

    @JmsListener(destination = "topic.guests", containerFactory = "topicFactory")
    public void receivedListOfGuestsFromTopic(List<Guest> guests){
        logger.info("Message received from topic: " + guests);
    }

    @JmsListener(destination = "topic.guests",containerFactory = "topicFactory" )
    public void receivedGuestFromTopic(Guest guest){
        logger.info("Message received from topic: " + guest);
    }

    @JmsListener(destination = "topic.ski-passes", containerFactory = "topicFactory")
    public void receivedListOfSkiPassesFromTopic(List<SkiPass> skiPasses){
        logger.info("Message received from topic: " + skiPasses);
    }

    @JmsListener(destination = "queue.user", containerFactory = "queueFactory")
    public void receivedUserFromTopic(User user){
        logger.info("Message received from queue: " + user);
    }

    @JmsListener(destination = "topic.guests", containerFactory = "topicFactory")
    public void receivedGuestDTOFromTopic(GuestDTO guestDTO){
        logger.info("Message received from topic: " + guestDTO);
        LocalDateTime skiPassDuration = guestDTO.getSkiPassDuration().plusDays(7);
        Guest guest = null;
        Optional<GuestDTO> optional = Optional.ofNullable(guestService.getGuest(guestDTO.getId()));
        if(optional.isPresent()){
            guest = guestRepository.findById(guestDTO.getId()).get();
            SkiPass skiPass = skiPassRepository.findById(guestDTO.getSkiPassId()).get();
            skiPass.setDuration(skiPassDuration);
            guest.setSkiPass(skiPass);
        }
        guestService.updateGuest(guest);
    }
}
