CREATE SEQUENCE users_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE meals_id_seq START 1 INCREMENT 1;

CREATE TABLE users (
    id BIGINT PRIMARY KEY DEFAULT nextval('users_id_seq'),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    age INT NOT NULL,
    weight DECIMAL(10,2) NOT NULL,
    height DECIMAL(10,2) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    goal VARCHAR(50) NOT NULL
);

CREATE TABLE meals (
    id BIGINT PRIMARY KEY DEFAULT nextval('meals_id_seq'),
    name VARCHAR(255) NOT NULL,
    calories_per_portion DECIMAL(10,2) NOT NULL,
    proteins DECIMAL(10,2) NOT NULL,
    fats DECIMAL(10,2) NOT NULL,
    carbs DECIMAL(10,2) NOT NULL
);