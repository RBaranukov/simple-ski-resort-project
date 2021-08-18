package com.example.ski_resort.baranukov.activemq;

import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JMSConsumer {

    private static Logger logger = LoggerFactory.getLogger(JMSConsumer.class);

    @JmsListener(destination = "queue.coach")
    public void receivedCoachFromQueue(Coach coach) {
        logger.info("Message received from queue: " + coach);
    }

    @JmsListener(destination = "topic.guests")
    public void receivedListOfGuestsFromTopic(List<Guest> guests){
        logger.info("Message received from queue: " + guests);
    }

    @JmsListener(destination = "topic.guests")
    public void receivedGuestFromTopic(Guest guest){
        logger.info("Message received from topic: " + guest);
    }

    @JmsListener(destination = "topic.ski-passes")
    public void receivedListOfSkiPassesFromTopic(List<SkiPass> skiPasses){
        logger.info("Message received from queue: " + skiPasses);
    }

    @JmsListener(destination = "queue.user")
    public void receivedUserFromTopic(User user){
        logger.info("Message received from topic: " + user);
    }
}
