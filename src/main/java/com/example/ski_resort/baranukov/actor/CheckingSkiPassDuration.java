package com.example.ski_resort.baranukov.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.example.ski_resort.baranukov.service.GuestService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class CheckingSkiPassDuration {

    private final @NonNull GuestService guestService;

    ActorSystem system = ActorSystem.create("ski-resort");

    @Scheduled(cron = "* * 12 * * MON-SUN") // checks everyday at 12p.m. SkiPass duration
    public void lessThanOneDayOfSkiPass(){
        guestService.getAllGuests().forEach(guestDTO -> {
            LocalDateTime skiPassDuration = guestDTO.getSkiPassDuration();
            long oneDay = ChronoUnit.HOURS.between(skiPassDuration, LocalDateTime.now());
            // If there is less than one day left, inform the guest about it
            if (oneDay < 24) {
                ActorRef senderToMQ = system.actorOf(Props.create(ActorSenderToActiveMQ.class, ActorSenderToActiveMQ::new));
                senderToMQ.tell(guestDTO, ActorRef.noSender());
            }
        });
    }
}