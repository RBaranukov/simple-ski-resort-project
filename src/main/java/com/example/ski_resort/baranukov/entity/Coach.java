package com.example.ski_resort.baranukov.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "coaches")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "category")
    private String category;

    @Column(name = "sex")
    private char sex;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd.MM.yyyy")
    @Column (name = "birth_date")
    private LocalDate birthDate;

    @Column(columnDefinition = "LONGBLOB",name = "photo")
    private byte [] photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ski_pass_id")
    @JsonManagedReference(value = "coachSkiPass")
    private SkiPass skiPass;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Guest> guestList = new ArrayList<>();

    public Coach(String name, String surname, String category, char sex, LocalDate birthDate, byte[] photo, SkiPass skiPass) {
        this.name = name;
        this.surname = surname;
        this.category = category;
        this.sex = sex;
        this.birthDate = birthDate;
        this.photo = photo;
        this.skiPass = skiPass;
    }

    public Coach() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte [] photo) {
//        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
//            BufferedImage originalImage =
//                    ImageIO.read(new File(pathToPhoto));
//
//            ImageIO.write(originalImage, "jpg", baos);
//            baos.flush();
//            photo =  baos.toByteArray();
//
//            //save imageInByte as blob in database
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        this.photo = photo;
    }

    public SkiPass getSkiPass() {
        return skiPass;
    }

    public void setSkiPass(SkiPass skiPass) {
        this.skiPass = skiPass;
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return sex == coach.sex && Objects.equals(id, coach.id) && Objects.equals(name, coach.name)
                && Objects.equals(surname, coach.surname) && Objects.equals(category, coach.category)
                && Objects.equals(birthDate, coach.birthDate) && Arrays.equals(photo, coach.photo)
                && Objects.equals(skiPass, coach.skiPass);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, surname, category, sex, birthDate, skiPass, guestList);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
