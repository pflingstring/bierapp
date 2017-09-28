-- :name create-transaction! :<! :n
-- :doc creates a new transaction record
INSERT INTO transactions
(from_id, amount, date)
VALUES (:from_id, :amount, to_date(:date, 'YYYYMMDD'))
RETURNING id;

-- :name update-transaction! :! :n
-- :doc update an existing transaction record
UPDATE transactions
SET from_id = :from_id, amount = :amount, date = :date
WHERE id = :id;

-- :name get-transaction :? :1
-- :doc retrieve a transaction given the id.
SELECT * FROM transactions
WHERE id = :id;

-- :name delete-transaction! :! :n
-- :doc delete a transaction given the id
DELETE FROM transactions
WHERE id = :id;
