CREATE TABLE consumptions (
  id         SERIAL     PRIMARY KEY,
  user_id    INTEGER    NOT NULL        REFERENCES users(id),
  date       DATE       NOT NULL,
  rings      JSON       NOT NULL
)
