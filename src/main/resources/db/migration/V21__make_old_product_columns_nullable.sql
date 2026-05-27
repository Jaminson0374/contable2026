ALTER TABLE products
    ALTER COLUMN category         DROP NOT NULL,
    ALTER COLUMN tax_category     DROP NOT NULL,
    ALTER COLUMN unit_of_measure  DROP NOT NULL,
    ALTER COLUMN tax_rate         DROP NOT NULL,
    ALTER COLUMN is_transformable DROP NOT NULL;
