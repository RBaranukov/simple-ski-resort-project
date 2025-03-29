package com.example.ski_resort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableKafka
public class SkiResortApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkiResortApplication.class, args);
    }
}
