package com.example.ski_resort.baranukov.actor;

import akka.actor.*;
import com.example.ski_resort.baranukov.dto.GuestDTO;
import com.example.ski_resort.baranukov.service.GuestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActorSenderToActiveMQ extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(ActorSenderToActiveMQ.class);

    @Autowired
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
        logger.info("Migration actor started");
    }

    @Override
    public void postStop() throws Exception {
        logger.info("Migration actor stopped");
        super.postStop();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Send.class, this::onSend)
                .matchAny(o -> logger.info("received unknown message"))
                .build();
    }

    private void onSend(Send send){
        guestService.sendAndProlongSkiPass(send.guestDTO.getId());
        logger.info("Send guest to ActiveMQ topic");
    }
}
