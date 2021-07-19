package com.example.ski_resort.baranukov.repository.customized;

import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.entity.Guest;
import com.example.ski_resort.baranukov.entity.SkiPass;
import com.example.ski_resort.baranukov.exception.GuestNotFoundException;
import com.example.ski_resort.baranukov.exception.SkiPassNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class CustomizedSetSkiPassAndCoachImpl implements CustomizedSetSkiPassAndCoach {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void setSkiPassToGuest(Long skiPass_id, Long id) {
        Guest guest;
        SkiPass skiPass;

        if (entityManager.contains(entityManager.find(Guest.class, id))) {
            guest = entityManager.find(Guest.class, id);//search guest by ID
        } else throw new GuestNotFoundException(id);

        if(entityManager.contains(entityManager.find(SkiPass.class, skiPass_id))){
            skiPass = entityManager.find(SkiPass.class, skiPass_id);
        } else throw new SkiPassNotFoundException(skiPass_id);

        guest.setSkiPass(skiPass);
        entityManager.merge(guest);
        entityManager.close();
    }

    @Override
    @Transactional
    public void setCoachToGuest(Long coach_id, Long id) {
        Guest guest;
        Coach coach;

        if (entityManager.contains(entityManager.find(Guest.class, id))) {
            guest = entityManager.find(Guest.class, id);//search guest by ID
        } else throw new GuestNotFoundException(id);

        if(entityManager.contains(entityManager.find(Coach.class, coach_id))){
            coach = entityManager.find(Coach.class, coach_id);
        } else throw new SkiPassNotFoundException(coach_id);

        guest.setCoach(coach);
        entityManager.merge(guest);
        entityManager.close();
    }
}