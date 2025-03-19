CREATE SEQUENCE users_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE dishes_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE meal_id_seq START 1 INCREMENT 1;

CREATE TABLE users (
    id BIGINT PRIMARY KEY DEFAULT nextval('users_id_seq'),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    age INT NOT NULL,
    weight DECIMAL(10,2) NOT NULL,
    height DECIMAL(10,2) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    goal VARCHAR(50) NOT NULL,
    daily_calories DECIMAL(10,2) DEFAULT 0
);

CREATE TABLE dishes (
    id BIGINT PRIMARY KEY DEFAULT nextval('dishes_id_seq'),
    name VARCHAR(255) NOT NULL,
    calories_per_portion DECIMAL(10,2) NOT NULL,
    proteins DECIMAL(10,2) NOT NULL,
    fats DECIMAL(10,2) NOT NULL,
    carbs DECIMAL(10,2) NOT NULL
);

CREATE TABLE meals (
    id BIGINT PRIMARY KEY DEFAULT nextval('meal_id_seq'),
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    meal_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_calories DECIMAL(10, 2) NOT NULL
);

CREATE TABLE meal_dishes (
    meal_id BIGINT REFERENCES meals(id) ON DELETE CASCADE,
    dish_id BIGINT REFERENCES dishes(id) ON DELETE CASCADE,
    portion_size BIGINT CHECK (portion_size > 0),
    PRIMARY KEY (meal_id, dish_id)
);

CREATE INDEX idx_meals_user_meal_time ON meals(user_id, meal_time);