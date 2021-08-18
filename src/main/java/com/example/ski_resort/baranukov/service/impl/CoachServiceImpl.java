package com.example.ski_resort.baranukov.service.impl;

import com.example.ski_resort.baranukov.dto.CoachDTO;
import com.example.ski_resort.baranukov.entity.Coach;
import com.example.ski_resort.baranukov.exception.CoachNotFoundException;
import com.example.ski_resort.baranukov.repository.CoachRepository;
import com.example.ski_resort.baranukov.service.CoachService;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final JmsTemplate jmsProducer;

    @Override
    public List<CoachDTO> getAllCoaches() {
        return coachRepository.findAll()
                .stream()
                .map(CoachDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Coach save(Coach coach) {
        return coachRepository.save(coach);
    }

    public Coach updateCoach(Coach coach) {
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
    public CoachDTO getCoach(Long id) {
        return new CoachDTO(coachRepository.findById(id)
                .orElseThrow(() -> new CoachNotFoundException(id)));
    }

    @Override
    public void deleteCoach(Long id) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new CoachNotFoundException(id));
        Optional.ofNullable(coach.getGuests())
                .ifPresent(guests1 -> guests1
                        .forEach(guest -> guest.setCoach(null)));
        coachRepository.delete(coach);
    }

    @Override
    public void setPhotoToCoach(Long id, String pathNameToPhoto) {
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
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new CoachNotFoundException(id));
        jmsProducer.convertAndSend("queue.coach", coach);
    }
}
