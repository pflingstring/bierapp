-- :name create-log-entry! :<! :n
-- :doc creates a new log entry
INSERT INTO balance_log
(user_id, transaction_id, old_amount, new_amount)
VALUES (:user_id, :transaction_id, :old_amount, :new_amount)
RETURNING id;

-- :name update-log-entry! :! :n
UPDATE balance_log
SET new_amount = :new_amount
WHERE id = :id;

-- :name get-log-entry-by-transaction :? :1
SELECT id, old_amount
FROM balance_log
WHERE transaction_id = :transaction_id;