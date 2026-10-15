CREATE TABLE refresh_tokens (
        id UUID PRIMARY KEY,
        user_id VARCHAR(255) NOT NULL,
        token_hash VARCHAR(64) NOT NULL UNIQUE,
        expiration_date TIMESTAMP NOT NULL,
        revoked BOOLEAN NOT NULL DEFAULT FALSE,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_refresh_token_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);