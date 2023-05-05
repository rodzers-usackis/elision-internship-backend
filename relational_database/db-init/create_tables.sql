CREATE TABLE application_user (
                                  id SERIAL PRIMARY KEY,
                                  first_name VARCHAR(50) NOT NULL,
                                  last_name VARCHAR(50) NOT NULL,
                                  password VARCHAR(20) NOT NULL
);

INSERT INTO application_user (first_name, last_name, password)
VALUES ('John', 'Doe', 'password');