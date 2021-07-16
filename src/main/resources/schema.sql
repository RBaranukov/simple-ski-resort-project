create table guests (
    id INT NOT NULL  PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    surname VARCHAR(30),
    birth_date DATE,
    date_of_visit DATE,
    ski_pass_id INT,
    coach_id INT,
    FOREIGN KEY  (ski_pass_id) REFERENCES ski_passes(id),
    FOREIGN KEY (coach_id) REFERENCES coaches(id)
);

create table ski_passes (
    id INT NOT NULL  PRIMARY KEY AUTO_INCREMENT,
    duration DATE,
    cost INT
);

create table coaches (
    id INT NOT NULL  PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    surname VARCHAR(30),
    category VARCHAR(30),
    birthdate DATE,
    sex char,
    ski_pass_id INT,
    photo longblob,
    FOREIGN KEY (ski_pass_id) references ski_passes(id)
);