CREATE TABLE users (
 id            SERIAL          PRIMARY KEY,
 first_name    VARCHAR(30)     NOT NULL,
 last_name     VARCHAR(30)     NOT NULL,
 email         VARCHAR(30)     NOT NULL,
 pass          VARCHAR(50)     NOT NULL,
 balance       NUMERIC(7, 2)   NOT NULL
);

--;;

INSERT INTO users (first_name, last_name, email, pass, balance)
VALUES ('Bier', 'Kasse', 'bier@kasse.de', 'bierjunge', 0);

--;;

INSERT INTO users (first_name, last_name, email, pass, balance)
VALUES ('Haupt', 'Kasse', 'haput@kasse,de', 'spaarfux', 0);

--;;

ALTER SEQUENCE users_id_seq RESTART WITH 100;