package com.example.ski_resort.baranukov.repository;

import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.entity.SkiPass;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomizedSetSkiPassAndCoachImpl implements CustomizedSetSkiPassAndCoach{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public void setSkiPass(SkiPass skiPass, Long id) {
        Guest guest = entityManager.find(Guest.class, id);
        guest.setSkiPass(skiPass);
        entityManager.persist(guest);
    }

    @Override
    @Transactional
    public void setCoach(Coach coach, Long id) {
        Guest guest = entityManager.find(Guest.class, id);
        guest.setCoach(coach);
        entityManager.persist(guest);
    }
}
