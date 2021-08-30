package com.example.ski_resort.baranukov.actor;

import akka.actor.AbstractActor;
import com.example.ski_resort.baranukov.dto.GuestDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jms.core.JmsTemplate;

@AllArgsConstructor
@NoArgsConstructor
public class ActorSenderToActiveMQ extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(ActorSenderToActiveMQ.class);
    private JmsTemplate jmsProducer;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(m -> logger.warn("Unknown message: {}", m))
                .match(GuestDTO.class, this::processMessage)
                .build();
    }

    private void processMessage(final GuestDTO guestDTO){
        jmsProducer.convertAndSend("queue.guests", guestDTO);
    }
}
