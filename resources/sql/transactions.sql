-- :name create-transaction! :! :n
-- :doc creates a new transaction record
INSERT INTO transactions
(used_id, amount, date)
VALUES (:user_id, :amount, :date);

-- :name update-transaction! :! :n
-- :doc update an existing transaction record
UPDATE transactions
SET used_id = :user_id, amount = :amount, date = :date
WHERE id = :id;

-- :name get-transaction :? :1
-- :doc retrieve a transaction given the id.
SELECT * FROM transactions
WHERE id = :id;

-- :name delete-transaction! :! :n
-- :doc delete a transaction given the id
DELETE FROM transactions
WHERE id = :id;
