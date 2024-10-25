package com.example.ski_resort.baranukov.actor;

import akka.actor.*;
import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class ActorSenderToActiveMQ extends AbstractActor {

    private final GuestService guestService;

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
