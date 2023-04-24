CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE application_user (
                      id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                      first_name VARCHAR(50) NOT NULL,
                      last_name VARCHAR(50) NOT NULL,
                      password VARCHAR(20) NOT NULL
);

INSERT INTO application_user (id, first_name, last_name, password)
VALUES (uuid_generate_v4(), 'John', 'Doe', 'password');
