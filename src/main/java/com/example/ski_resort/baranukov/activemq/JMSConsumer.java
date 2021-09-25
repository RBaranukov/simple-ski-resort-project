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
    private final @NonNull SkiPassService skiPassService;
    private final @NonNull SkiPassRepository skiPassRepository;

    @JmsListener(destination = "queue.coach", containerFactory = "queueFactory")
    public void receivedCoachFromQueue(CoachDTO coach) {
        logger.info("Message received from queue: " + coach);
    }

    @JmsListener(destination = "topic.guests", containerFactory = "topicFactory")
    public void receivedListOfGuestsFromTopic(List<GuestDTO> guests){
        logger.info("Message received from topic: " + guests);
    }

    @JmsListener(destination = "topic.guest",containerFactory = "topicFactory" )
    public void receivedGuestFromTopic(GuestDTO guest){
        logger.info("Message received from topic: " + guest);
    }

    @JmsListener(destination = "topic.ski-passes", containerFactory = "topicFactory")
    public void receivedListOfSkiPassesFromTopic(List<SkiPassDTO> skiPasses){
        logger.info("Message received from topic: " + skiPasses);
    }

    @JmsListener(destination = "queue.user", containerFactory = "queueFactory")
    public void receivedUserFromTopic(User user){
        logger.info("Message received from queue: " + user);
    }

    @JmsListener(destination = "topic.guestProlongation", containerFactory = "topicFactory")
    public void receivedGuestDTOFromTopic(GuestDTO guestDTO){
        logger.info("Message received from topic: " + guestDTO);
        LocalDateTime skiPassDuration = LocalDateTime.now().plusDays(8);
        Optional<GuestDTO> optional = Optional.ofNullable(guestService.getGuest(guestDTO.getId()));
        if(optional.isPresent()){
            SkiPass skiPass = skiPassRepository.findById(guestDTO.getSkiPassId()).get();
            skiPass.setDuration(skiPassDuration);
            skiPassService.updateSkiPass(skiPass);
        }
        logger.info("SkiPass was updated");
    }
}
