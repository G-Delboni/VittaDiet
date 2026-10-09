CREATE TABLE food (
    id BIGSERIAL PRIMARY KEY,
    taco_code INTEGER NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    calorie_per_hundred_grams DOUBLE PRECISION NOT NULL,
    protein_per_hundred_grams DOUBLE PRECISION,
    carbohydrate_per_hundred_grams DOUBLE PRECISION,
    fat_per_hundred_grams DOUBLE PRECISION
);