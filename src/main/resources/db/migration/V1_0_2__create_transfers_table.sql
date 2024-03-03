CREATE TABLE IF NOT EXISTS transfers (
    id SERIAL PRIMARY KEY,
    amount DECIMAL(19, 2) NOT NULL,
    date TIMESTAMP NOT NULL DEFAULT now(),
    from_account_id INT NOT NULL,
    to_account_id INT NOT NULL,
    FOREIGN KEY (from_account_id) REFERENCES accounts(id),
    FOREIGN KEY (to_account_id) REFERENCES accounts(id)
);