CREATE TABLE IF NOT EXISTS collections (
    id                UUID PRIMARY KEY,
    client_id         UUID            NOT NULL REFERENCES third_parties(id),
    ar_id             UUID            NOT NULL REFERENCES accounts_receivable(id),
    due_date          DATE            NOT NULL,
    status            VARCHAR(20)     NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING','CONTACTED','PROMISED','PAID','DISPUTED')),
    last_contact_date DATE,
    contact_method    VARCHAR(30),
    contact_notes     TEXT,
    assigned_to       VARCHAR(100),
    created_at        TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_col_client ON collections(client_id);
CREATE INDEX IF NOT EXISTS idx_col_status ON collections(status);
CREATE INDEX IF NOT EXISTS idx_col_due_date ON collections(due_date);
