CREATE TABLE meal_item (
    id BIGSERIAL PRIMARY KEY,

    meal_id BIGINT NOT NULL,
    food_id BIGINT NOT NULL,

    quantity_in_grams DOUBLE PRECISION NOT NULL,

    CONSTRAINT fk_meal_item_meal
    FOREIGN KEY (meal_id)
    REFERENCES meal(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_meal_item_food
    FOREIGN KEY (food_id)
    REFERENCES food(id)
);