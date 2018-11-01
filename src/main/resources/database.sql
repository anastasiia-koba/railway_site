DROP TABLE user_roles, users, roles, routs_by_sections, routs, rout_section,
           trains_routs, trains, stations;

-- Table: users
CREATE TABLE users (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  surname  VARCHAR(255),
  firstname VARCHAR(255),
  birthdate DATE
)
  ENGINE = InnoDB;

-- Table: roles
CREATE TABLE roles (
  id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
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
  stationname VARCHAR(255) NOT NULL
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

  FOREIGN KEY (departure_id) REFERENCES stations (id) ,
  FOREIGN KEY (destination_id) REFERENCES stations (id)
)
  ENGINE = InnoDB;

-- Table: routs
CREATE TABLE routs (
  id       INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  -- step_rout_id INT NOT NULL,
  -- station_id INT NOT NULL,
  -- departure_time TIME NOT NULL,
  -- arrival_time TIME NOT NULL,
  start_station_id INT NOT NULL,
  end_station_id INT NOT NULL,

  FOREIGN KEY (start_station_id) REFERENCES stations (id),
  FOREIGN KEY (end_station_id) REFERENCES stations (id)
)
  ENGINE = InnoDB;


-- Table for mapping rout_section and rout_id
CREATE TABLE routs_by_sections (
  rout_id INT NOT NULL,
  rout_section_id INT NOT NULL,

  -- departure_time TIME NOT NULL,
  -- arrival_time TIME NOT NULL,

  FOREIGN KEY (rout_id) REFERENCES routs (id),
  FOREIGN KEY (rout_section_id) REFERENCES rout_section (id),

  UNIQUE (rout_section_id, rout_id)
)
  ENGINE = InnoDB;



-- Table: trains
CREATE TABLE trains(
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  trainname VARCHAR(255) NOT NULL,
  places_number INT NOT NULL
)
  ENGINE = InnoDB;

-- Table: trains_routs
CREATE TABLE trains_routs (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  train_id INT NOT NULL,
  routs_id INT NOT NULL,
  date DATE NOT NULL,

  FOREIGN KEY (train_id) REFERENCES trains (id) ,
  FOREIGN KEY (routs_id) REFERENCES routs (id)
)
  ENGINE = InnoDB;


-- Insert data

INSERT INTO users VALUES (1, 'admin', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG', null, null, null );

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);

INSERT INTO stations VALUES (1, 'Moscow');
INSERT INTO stations VALUES (2, 'Saint Petersburg');

INSERT INTO rout_section VALUES (1, 1, 2, 714, 500, "11:12:30", "21:00:00");
INSERT INTO routs VALUES (1, 1, 2);
INSERT INTO routs_by_sections VALUES (1, 1);
