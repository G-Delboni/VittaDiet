ALTER TABLE meal_item
ALTER COLUMN food_id DROP NOT NULL,
ADD COLUMN personal_food_id BIGINT,
ADD CONSTRAINT fk_meal_item_personal_food
    FOREIGN KEY (personal_food_id)
    REFERENCES personal_food(id),
-- exatamente um dos dois deve estar preenchido
ADD CONSTRAINT ck_meal_item_one_food CHECK (
    (food_id IS NOT NULL)::int + (personal_food_id IS NOT NULL)::int = 1
);