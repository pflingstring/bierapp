CREATE TABLE cash_log (
  id                SERIAL           PRIMARY KEY,
  transaction_id    INTEGER          NOT NULL        REFERENCES transactions(id),
  amount            NUMERIC(7, 2)    NOT NULL
);