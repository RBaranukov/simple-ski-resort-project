package com.example.ski_resort.baranukov.actor;

import akka.actor.AbstractActor;
import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
@RequiredArgsConstructor
public class ActorSenderToActiveMQ extends AbstractActor {
    private GuestService guestService;

    public static class Send{
        public final GuestDTO guestDTO;

        public Send(GuestDTO guestDTO) {
            this.guestDTO = guestDTO;
        }
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        log.info("Migration actor started");
    }

    @Override
    public void postStop() throws Exception {
        log.info("Migration actor stopped");
        super.postStop();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Send.class, this::onSend)
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private void onSend(Send send){
        guestService.sendAndProlongSkiPass(send.guestDTO.getId());
        log.info("Send guest to ActiveMQ topic");
    }
}
