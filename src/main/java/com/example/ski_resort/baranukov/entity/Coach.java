package com.example.ski_resort.baranukov.entity;


import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coaches")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column (name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "photo")
    private byte [] photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ski_pass_id")
    private SkiPass skiPass;

    @OneToMany(mappedBy = "coach", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
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
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(String pathToPhoto) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage originalImage =
                    ImageIO.read(new File(pathToPhoto));

            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            photo = baos.toByteArray();
            //save imageInByte as blob in database
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
}
