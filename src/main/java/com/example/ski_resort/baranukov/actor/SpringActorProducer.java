package com.example.ski_resort.baranukov.actor;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class SpringActorProducer implements IndirectActorProducer {

    private final String actorBeanName;
    private final ApplicationContext applicationContext;

    @Override
    public Actor produce() {
        return (Actor) applicationContext.getBean(actorBeanName);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext.getType(actorBeanName);
    }
}
