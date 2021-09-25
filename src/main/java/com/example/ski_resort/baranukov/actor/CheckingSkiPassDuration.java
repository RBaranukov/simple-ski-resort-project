package com.example.ski_resort.baranukov.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
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

    private final @NonNull ActorSystem actorSystem;
    private final @NonNull GuestService guestService;
    private final @NonNull SpringExtension springExtension;

    @Scheduled(cron = "0 16 15 * * *") // checks everyday at 12p.m. SkiPass duration
    public void lessThanOneDayOfSkiPass() {
        guestService.getAllGuests()
                .forEach(guestDTO -> {
                    LocalDateTime skiPassDuration = guestDTO.getSkiPassDuration();
                    long oneDay = ChronoUnit.HOURS.between(LocalDateTime.now(), skiPassDuration);
                    // If there is less than one day left, inform the guest about it
                    if (oneDay < 24) {
                        ActorRef senderToMQ = actorSystem.actorOf(springExtension.props("actorSenderToActiveMQ"), "sender");
                        senderToMQ.tell(guestDTO, ActorRef.noSender());
                    }
                });
    }
}