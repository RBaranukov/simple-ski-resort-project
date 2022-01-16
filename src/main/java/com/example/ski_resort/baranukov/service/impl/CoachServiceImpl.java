package com.example.ski_resort.baranukov.service.impl;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.exception.CoachNotFoundException;
import com.example.ski_resort.baranukov.repository.CoachRepository;
import com.example.ski_resort.baranukov.service.CoachService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final JmsTemplate jmsProducer;

    @Override
    public Collection<CoachDTO> getAll() {
        log.info("Get all coaches");
        return coachRepository.findAll()
                .stream()
                .map(CoachDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Coach save(Coach coach) {
        log.info("Save coach");
        return coachRepository.save(coach);
    }

    public Coach update(Coach coach) {
        log.info("Update coach");
        Optional<Coach> optional = coachRepository.findById(coach.getId());
        if (optional.isPresent()) {
            Coach updateCoach = optional.get();
            updateCoach.setId(coach.getId());
            updateCoach.setName(coach.getName());
            updateCoach.setSurname(coach.getSurname());
            updateCoach.setBirthDate(coach.getBirthDate());
            updateCoach.setGuests(coach.getGuests());
            updateCoach.setSex(coach.getSex());
            updateCoach.setCategory(coach.getCategory());
            return coachRepository.save(updateCoach);
        } else throw new CoachNotFoundException(coach.getId());
    }

    @Override
    public CoachDTO get(Long id) {
        log.info("Get coach");
        return new CoachDTO(coachRepository.findById(id)
                .orElseThrow(() -> new CoachNotFoundException(id)));
    }

    @Override
    public void delete(Long id) {
        log.info("Delete coach");
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new CoachNotFoundException(id));
        Optional.ofNullable(coach.getGuests())
                .ifPresent(guests1 -> guests1
                        .forEach(guest -> guest.setCoach(null)));
        coachRepository.delete(coach);
    }

    @Override
    public void setPhotoToCoach(Long id, String pathNameToPhoto) {
        log.info("Set photo to coach");
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new CoachNotFoundException(id));

        byte[] photo = null;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage originalImage =
                    ImageIO.read(new File(pathNameToPhoto));

            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            photo = baos.toByteArray();

            //save imageInByte as blob in database
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        coach.setPhoto(photo);
        coachRepository.save(coach);
    }

    @Override
    public void sendCoach(Long id) {
        log.info("Send coach");
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new CoachNotFoundException(id));
        CoachDTO coachDTO = new CoachDTO(coach);
        jmsProducer.convertAndSend("queue.coach", coachDTO);
    }
}
