CREATE TABLE transactions (
  id         SERIAL     PRIMARY KEY,
  used_id    INTEGER    REFERENCES users(id),
  amount     MONEY,
  date       DATE
)