-- :name create-log-entry! :<! :n
-- :doc creates a new log entry
INSERT INTO cash_log
(transaction_id, amount)
VALUES (:transaction_id, :amount)
RETURNING id;

-- :name update-cash-balance! :! :n
-- :doc updates the balance after a transaction was made
UPDATE cash_log
SET amount = amount + :amount
WHERE id = :id;