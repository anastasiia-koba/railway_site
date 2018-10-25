DROP TABLE user_roles, users, roles, stations, routs

-- Table: users
CREATE TABLE users (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
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

-- Table: routs
CREATE TABLE step_routs (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  departure_id INT NOT NULL,
  destination_id INT NOT NULL,

  FOREIGN KEY (departure_id) REFERENCES stations (id),
  FOREIGN KEY (destination_id) REFERENCES stations (id)

)
  ENGINE = InnoDB;


-- Insert data

INSERT INTO users VALUES (1, 'proselyte', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);

INSERT INTO stations VALUES (1, 'Moscow');
INSERT INTO stations VALUES (2, 'Saint Petersburg');

INSERT INTO step_routs VALUES (1, 1, 2);