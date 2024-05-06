CREATE TABLE favours (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    created_at timestamp NOT NULL DEFAULT current_timestamp
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    middle_name VARCHAR(255),
    password VARCHAR(255),
    role_user VARCHAR(255),
    credentials_expiry_date timestamp,
    is_account_non_expired BOOLEAN,
    is_account_non_locked BOOLEAN,
    is_active BOOLEAN NOT NULL,
    is_enabled BOOLEAN,
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
);

CREATE TABLE user_device (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    device_type VARCHAR(255),
    device_id VARCHAR(255),
    is_refresh_active BOOLEAN,
    created_at timestamp NOT NULL DEFAULT current_timestamp,
    updated_at timestamp,
    CONSTRAINT fk_user_device_users FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE refresh_token (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_device_id INT UNIQUE,
    refresh_count INT,
    expire_date timestamp,
    created_at timestamp NOT NULL DEFAULT current_timestamp,
    updated_at timestamp,
    CONSTRAINT fk_refresh_token_user_device FOREIGN KEY (user_device_id) REFERENCES user_device(id)
);
