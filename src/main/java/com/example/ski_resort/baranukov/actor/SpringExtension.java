package com.example.ski_resort.baranukov.actor;


import akka.actor.Extension;
import akka.actor.Props;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Baranukov Rakhim.
 *
 * An Akka Extension to provide access to Spring managed Actor Beans.
 *
 * The Extension implementation.
 */

@Component
public class SpringExtension implements Extension {

    private ApplicationContext applicationContext;

    /**
     * Used to initialize the Spring application context for the extension.
     *
     * @param applicationContext
     */
    public void initialize(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Create a Props for the specified actorBeanName using the
     * SpringActorProducer class.
     *
     * @param actorBeanName The name of the actor bean to create Props for
     * @return a Props that will create the named actor bean using Spring
     */
    public Props props(String actorBeanName) {
        return Props.create(SpringActorProducer.class,
                applicationContext, actorBeanName);
    }
}
