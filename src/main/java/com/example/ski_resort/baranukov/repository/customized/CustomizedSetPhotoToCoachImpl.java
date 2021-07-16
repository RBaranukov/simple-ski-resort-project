package com.example.ski_resort.baranukov.repository.customized;

import com.example.ski_resort.baranukov.entity.Coach;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
public class CustomizedSetPhotoToCoachImpl implements CustomizedSetPhotoToCoach{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public void setPhotoToCoach(Coach coach, String pathNameToPhoto){
        // Use Image here or in setter(Coach Entity)???
        byte [] photo = null;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage originalImage =
                    ImageIO.read(new File(pathNameToPhoto));

            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            photo =  baos.toByteArray();

            //save imageInByte as blob in database
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        coach.setPhoto(photo);
    }
}
