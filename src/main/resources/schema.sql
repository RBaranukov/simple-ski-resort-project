create table if not exists guests (
    id LONG PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    surname VARCHAR(50),
    birth_date DATE,
    date_of_visit DATE,
    ski_pass_id INT,
    coach_id INT,
    FOREIGN KEY  (ski_pass_id) REFERENCES ski_passes(id),
    FOREIGN KEY (coach_id) REFERENCES coaches(id)
);

create table if not exists ski_passes (
    id LONG PRIMARY KEY AUTO_INCREMENT,
    duration DATE,
    cost INT
);

create table if not exists coaches (
    id LONG PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    surname VARCHAR(50),
    category VARCHAR(50),
    birth_date DATE,
    sex CHAR,
    photo LONGBLOB,
    ski_pass_id INT,
    guest_id INT,
    FOREIGN KEY (ski_pass_id) references ski_passes(id),
    FOREIGN KEY (guest_id) references guests(id)
);