DROP TABLE tickets, user_roles, users, roles, routs_by_sections, routs, rout_section,
           trains_routs, trains, stations;

-- Table: users
CREATE TABLE users (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  surname  VARCHAR(255),
  firstname VARCHAR(255),
  birthdate DATE,
  deleted BIT NOT NULL DEFAULT 0
)
  ENGINE = InnoDB;

-- Table: roles
CREATE TABLE roles (
  id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  deleted BIT NOT NULL DEFAULT 0
)
  ENGINE = InnoDB;

-- Table for mapping user and roles: user_roles
CREATE TABLE user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
)
  ENGINE = InnoDB;


-- Table: stations
CREATE TABLE stations (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  stationname VARCHAR(255) NOT NULL,
  deleted BIT NOT NULL DEFAULT 0
)
  ENGINE = InnoDB;

-- Table: rout_section
CREATE TABLE rout_section (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  departure_id INT NOT NULL,
  destination_id INT NOT NULL,
  distance INT NOT NULL,
  price INT NOT NULL,
  departure_time TIME NOT NULL,
  arrival_time TIME NOT NULL,
  deleted BIT NOT NULL DEFAULT 0,

  FOREIGN KEY (departure_id) REFERENCES stations (id) ,
  FOREIGN KEY (destination_id) REFERENCES stations (id)
)
  ENGINE = InnoDB;

-- Table: routs
CREATE TABLE routs (
  id       INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  rout_name VARCHAR(255) NOT NULL,
  start_station_id INT NOT NULL,
  end_station_id INT NOT NULL,
  deleted BIT NOT NULL DEFAULT 0,

  FOREIGN KEY (start_station_id) REFERENCES stations (id),
  FOREIGN KEY (end_station_id) REFERENCES stations (id)
)
  ENGINE = InnoDB;


-- Table for mapping rout_section and rout_id
CREATE TABLE routs_by_sections (
  rout_id INT NOT NULL,
  rout_section_id INT NOT NULL,
  deleted BIT NOT NULL DEFAULT 0,

  FOREIGN KEY (rout_id) REFERENCES routs (id),
  FOREIGN KEY (rout_section_id) REFERENCES rout_section (id),

  UNIQUE (rout_section_id, rout_id, deleted)
)
  ENGINE = InnoDB;


-- Table: trains
CREATE TABLE trains(
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  trainname VARCHAR(255) NOT NULL,
  places_number INT NOT NULL,
  deleted BIT NOT NULL DEFAULT 0
)
  ENGINE = InnoDB;

-- Table: trains_routs
CREATE TABLE trains_routs (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  train_id INT NOT NULL,
  rout_id INT NOT NULL,
  date DATE NOT NULL,
  deleted BIT NOT NULL DEFAULT 0,

  FOREIGN KEY (train_id) REFERENCES trains (id) ,
  FOREIGN KEY (rout_id) REFERENCES routs (id),

  UNIQUE (train_id, rout_id, date, deleted)
)
  ENGINE = InnoDB;

-- Table: tickets
CREATE TABLE tickets (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  train_rout_id INT NOT NULL,
  price INT NOT NULL,
  start_station_id INT NOT NULL,
  end_station_id INT NOT NULL,
  deleted BIT NOT NULL DEFAULT 0,

  FOREIGN KEY (user_id) REFERENCES users (id) ,
  FOREIGN KEY (train_rout_id) REFERENCES trains_routs (id),
  FOREIGN KEY (start_station_id) REFERENCES stations (id),
  FOREIGN KEY (end_station_id) REFERENCES stations (id)
)
  ENGINE = InnoDB;


-- Insert data

INSERT INTO users VALUES (1, 'admin', '$2a$10$45wOcCHPJvqgoGGH1OGkBOsz41taOaQfIOiTfrFVrUAB9OJtEtyLC', null, null, null, 0);
INSERT INTO users VALUES (2, 'user', '$2a$10$L27PfL99nAwCsT/fyK7cH.wyyOlNCn9RTQwb1Aw8zwPGpN.Xw1qwq', 'Ivanov', 'Ivan', '1992.10.21', 0);

INSERT INTO roles VALUES (1, 'ROLE_USER', 0);
INSERT INTO roles VALUES (2, 'ROLE_ADMIN', 0);

INSERT INTO user_roles VALUES (1, 2);
INSERT INTO user_roles VALUES (2, 1);

INSERT INTO stations VALUES (1, 'Jerusalem', 0);
INSERT INTO stations VALUES (2, 'Haifa', 0);
INSERT INTO stations VALUES (3, 'Tel Aviv', 0);

INSERT INTO rout_section VALUES (1, 1, 3, 340, 300, "11:12:00", "15:30:00", 0);
INSERT INTO rout_section VALUES (2, 3, 2, 450, 350, "15:45:00", "21:00:00", 0);

INSERT INTO routs VALUES (1, '001', 1, 2, 0);
INSERT INTO routs_by_sections VALUES (1, 1);
INSERT INTO routs_by_sections VALUES (1, 2);

INSERT INTO trains VALUES (1, 'T-1', 50, 0);
INSERT INTO trains_routs VALUES (1, 1, 1, '2018.11.05', 0);

INSERT INTO tickets VALUES (1, 2, 1, '350', 3, 2, 0);
