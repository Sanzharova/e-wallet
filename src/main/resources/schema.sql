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

