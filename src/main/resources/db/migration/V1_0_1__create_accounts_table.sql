CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY,
    number INT NOT NULL,
    digit INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL,
    person_id INT NOT NULL,
    UNIQUE (number, digit),
    FOREIGN KEY (person_id) REFERENCES persons(id)
);