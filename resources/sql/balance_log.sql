-- :name create-log-entry! :<! :n
-- :doc creates a new log entry
INSERT INTO balance_log
(user_id, transaction_id, old_amount, new_amount)
VALUES (:user_id, :transaction_id, :old_amount, :new_amount)
RETURNING id;