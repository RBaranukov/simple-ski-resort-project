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
public class ActorSenderToActiveMQ extends UntypedAbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(ActorSenderToActiveMQ.class);

    @Autowired
    private GuestService guestService;

    @Override
    public void onReceive(Object message) {
        if(message instanceof GuestDTO){
            GuestDTO guestDTO = (GuestDTO) message;
            guestService.sendAndProlongSkiPass(guestDTO.getId());
            logger.info("Send guest to ActiveMQ topic");
        } else unhandled(message);
    }
}
