CREATE TABLE consumptions (
  id                SERIAL     PRIMARY KEY,
  user_id           INTEGER    NOT NULL        REFERENCES users(id),
  transaction_id    INTEGER    NOT NULL        REFERENCES transactions(id),
  date              DATE       NOT NULL,
  rings             JSONB      NOT NULL
);