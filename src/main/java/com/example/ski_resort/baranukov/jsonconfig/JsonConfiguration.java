package com.example.ski_resort.baranukov.jsonconfig;


import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
        This class let us set fetch type to LAZY initialization
 */

@Configuration
public class JsonConfiguration {

    @Bean
    public Module hibernateModule(){
        Hibernate5Module hibernate5Module = new Hibernate5Module();
//         lazy-loaded object should be forced to be loaded and then serialized (true);
        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
        return hibernate5Module;
    }
}
