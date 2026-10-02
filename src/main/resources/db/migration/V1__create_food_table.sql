CREATE TABLE food (
    id BIGSERIAL PRIMARY KEY,
    taco_code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    calories_per_100g DECIMAL(10, 2) NOT NULL,
    protein_per_100g DECIMAL(10, 2),
    carbohydrates_per_100g DECIMAL(10, 2),
    fat_per_100g DECIMAL(10, 2)
);