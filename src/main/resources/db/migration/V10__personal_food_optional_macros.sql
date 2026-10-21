ALTER TABLE personal_food
    ALTER COLUMN protein_per_hundred_grams      DROP NOT NULL,
    ALTER COLUMN carbohydrate_per_hundred_grams DROP NOT NULL,
    ALTER COLUMN fat_per_hundred_grams          DROP NOT NULL;