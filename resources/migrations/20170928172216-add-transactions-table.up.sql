CREATE TABLE transactions (
  id                SERIAL           PRIMARY KEY,
  from_id           INTEGER          NOT NULL        REFERENCES users(id),
  to_user           INTEGER          NOT NULL        REFERENCES users(id)     DEFAULT 1,
  amount            NUMERIC(6, 2)    NOT NULL,
  date              DATE             NOT NULL,
  info              TEXT             NOT NULL        DEFAULT '[!]'
)