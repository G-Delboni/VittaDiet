CREATE TABLE meal_item (
    id BIGSERIAL PRIMARY KEY,

    meal_id BIGINT NOT NULL,
    food_id BIGINT NOT NULL,

    quantity_grams DECIMAL(10, 2) NOT NULL,
    calories DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_meal_item_meal
    FOREIGN KEY (meal_id)
    REFERENCES meal(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_meal_item_food
    FOREIGN KEY (food_id)
    REFERENCES food(id)
);