package com.example.ski_resort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SkiResortBaranukovApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkiResortBaranukovApplication.class, args);
    }
}
