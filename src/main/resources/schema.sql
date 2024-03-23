CREATE TABLE favours (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    created_at timestamp NOT NULL DEFAULT current_timestamp
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    middle_name VARCHAR(255),
    created_at timestamp NOT NULL DEFAULT current_timestamp
);

CREATE TABLE wallets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL DEFAULT 0,
    created_at timestamp NOT NULL DEFAULT current_timestamp,
    user_id INT NOT NULL,
    CONSTRAINT wallets_user_id UNIQUE (user_id),
    CONSTRAINT fk_wallets_users FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE payments(
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255),
    created_at timestamp NOT NULL DEFAULT current_timestamp,
    updated_at timestamp,
    amount DECIMAL(19,2) NOT NULL DEFAULT 0,
    account_check VARCHAR(255),
    is_checked BOOLEAN NOT NULL DEFAULT FALSE,
    is_finished BOOLEAN NOT NULL DEFAULT FALSE,
    favour_id INT,
    wallet_id INT,
    CONSTRAINT fk_payments_favours FOREIGN KEY (favour_id) REFERENCES favours(id),
    CONSTRAINT fk_payments_users FOREIGN KEY (wallet_id) REFERENCES wallets(id)
)