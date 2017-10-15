CREATE TABLE balance_log (
  id                SERIAL           PRIMARY KEY,
  user_id           INTEGER          NOT NULL         REFERENCES users(id),
  transaction_id    INTEGER          NOT NULL         REFERENCES transactions(id),
  old_amount        NUMERIC(7, 2)    NOT NULL,
  new_amount        NUMERIC(7, 2)    NOT NULL
);