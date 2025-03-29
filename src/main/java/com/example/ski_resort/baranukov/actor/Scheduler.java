package com.example.ski_resort.baranukov.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.service.GuestService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final GuestService guestService;
    private final ActorSystem actorSystem;
    private final SpringExtension springExtension;
    private ActorRef actor;

    @Scheduled(cron = "0 0 12 * * *") // checks every day at 12 p.m. SkiPass duration
    public void checkSkiPassDuration() {
        guestService.getAll().forEach(this::sendEmailToGuest);
    }

    private void sendEmailToGuest(final GuestDTO guestDTO) {
        LocalDateTime skiPassDuration = guestDTO.getSkiPassDuration();
        long oneDay = ChronoUnit.HOURS.between(LocalDateTime.now(), skiPassDuration);
        // If there is less than one day left, inform the guest about it
        if (oneDay < 24) {
            actor.tell(new ActorSenderToActiveMQ.Send(guestDTO), ActorRef.noSender());
        }
    }

    /**
     * Метод postConstructMethod создает reference на migrationActor,
     * сообщения к которому будут попадать через роутер RoundRobinPool длиной 1000,
     * в итоге одновременно будут работать 1000 инстансов MigrationActor, каждый в своем потоке.
     * В итоге сообщения в коде шлем одному и тому же объекту — а роутер раскидывает их по разным инстансам
     */
    @PostConstruct
    private void postConstructMethod() {
//        ActorSystem system = applicationContext.getBean(ActorSystem.class);
//        SpringExtension springExtension = applicationContext.getBean(SpringExtension.class);

        // Use the Spring Extension to create props for a named actor bean
        actor = actorSystem.actorOf(springExtension.props("actorSenderToActiveMQ"));
    }
}