CREATE TABLE personal_food (
   id BIGSERIAL PRIMARY KEY,
   user_id VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,

   calorie_per_hundred_grams      DOUBLE PRECISION NOT NULL,
   protein_per_hundred_grams      DOUBLE PRECISION NOT NULL,
   carbohydrate_per_hundred_grams DOUBLE PRECISION NOT NULL,
   fat_per_hundred_grams          DOUBLE PRECISION NOT NULL,

   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

   CONSTRAINT fk_personal_food_user
       FOREIGN KEY (user_id)
           REFERENCES users(id)
           ON DELETE CASCADE,

   CONSTRAINT ck_personal_food_name CHECK (length(trim(name)) > 0),
   CONSTRAINT ck_personal_food_values CHECK (
       calorie_per_hundred_grams      >= 0 AND
       protein_per_hundred_grams      >= 0 AND
       carbohydrate_per_hundred_grams >= 0 AND
       fat_per_hundred_grams          >= 0
       )
);

-- Cada usuário não repete o próprio nome ("Pão Caseiro" = "pão caseiro").
-- O índice começa por user_id, então também serve para listar os alimentos de um usuário.
CREATE UNIQUE INDEX uq_personal_food_user_name
ON personal_food (user_id, lower(name));