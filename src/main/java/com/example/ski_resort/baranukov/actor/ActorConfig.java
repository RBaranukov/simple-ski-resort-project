package com.example.ski_resort.baranukov.actor;

import akka.actor.ActorSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ActorConfig {
    private final ApplicationContext applicationContext;
    private final SpringExtension springExtension;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem actorSystem = ActorSystem.create("ski-resort");
        springExtension.initialize(applicationContext);
        return actorSystem;
    }
}
