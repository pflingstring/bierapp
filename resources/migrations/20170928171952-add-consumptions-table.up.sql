CREATE TABLE consumptions (
  id         SERIAL     PRIMARY KEY,
  user_id    INTEGER    REFERENCES users(id),
  date       DATE,
  rings      JSON
)
