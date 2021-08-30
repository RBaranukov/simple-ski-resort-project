package com.example.ski_resort.baranukov.actor;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;

@AllArgsConstructor
public class SpringActorProducer implements IndirectActorProducer {
    private final ApplicationContext applicationContext;
    private final String actorBeanName;

    @Override
    public Actor produce() {
            return (Actor) applicationContext.getBean(actorBeanName);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext.getType(actorBeanName);
    }
}
