-- V4__expand_confirmation_token_user_account.sql

-- 1) dodaj nową kolumnę (nullable, bo to migracja kompatybilna)
ALTER TABLE confirmation_token
    ADD COLUMN IF NOT EXISTS user_account_id BIGINT;

-- 2) backfill: jeśli masz starą kolumnę user_id wskazującą users(id)
--    i user_account.user_id jest UNIQUE (masz), to mapowanie jest jednoznaczne
UPDATE confirmation_token ct
SET user_account_id = ua.id
FROM user_account ua
WHERE ct.user_account_id IS NULL
  AND ct.user_id = ua.user_id;

-- 3) indeks pod joiny i wyszukiwanie tokenów po user_account
CREATE INDEX IF NOT EXISTS idx_confirmation_token_user_account_id
    ON confirmation_token(user_account_id);

-- 4) FK (nie NOT NULL, żeby nie wywalić starych rekordów)
ALTER TABLE confirmation_token
    ADD CONSTRAINT fk_confirmation_token_user_account
        FOREIGN KEY (user_account_id) REFERENCES user_account(id);
