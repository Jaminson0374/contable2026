ALTER TABLE accounts_receivable ADD COLUMN IF NOT EXISTS interest_rate NUMERIC(5,2);
ALTER TABLE accounts_receivable ADD COLUMN IF NOT EXISTS interest_amount NUMERIC(15,2) DEFAULT 0;
ALTER TABLE accounts_receivable ADD COLUMN IF NOT EXISTS last_interest_calc_date DATE;
