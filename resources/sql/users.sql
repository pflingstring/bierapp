----------------------------
---   BASIC OPERATIONS   ---
----------------------------

-- :name create-user! :<! :n
-- :doc creates a new user record
INSERT INTO users
(first_name, last_name, email, pass, balance)
VALUES (:first_name, :last_name, :email, :pass, :balance)
RETURNING id;

-- :name update-user! :! :n
-- :doc update an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id;

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id;

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id;

-- :name get-all-users :? :*
SELECT id, first_name, last_name
FROM users;


------------------------
---   USER BALANCE   ---
------------------------

-- :name update-user-balance! :! :n
-- :doc updates user's account balance
UPDATE users
SET balance = :balance
WHERE id = :id;

-- :name get-user-balance :? :1
-- retrieve user balance given the id
SELECT balance
FROM users
WHERE id = :id;
