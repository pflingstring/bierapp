-- :name create-consumption! :! :n
-- :doc creates a new consumption record
INSERT INTO consumptions
(user_id, rings, transaction_id, date)
VALUES (:user_id, :rings, :transaction_id, to_date(:date, 'YYYYMMDD'));

-- :name update-consumption! :! :n
-- :doc update an existing consumption record
UPDATE consumptions
SET user_id = :user_id, date = :date, rings = :rings
WHERE id = :id;

-- :name get-consumption :? :1
-- :doc retrieve a consumption given the id.
SELECT * FROM consumptions
WHERE id = :id;

-- :name delete-consumption! :! :n
-- :doc delete a consumption given the id
DELETE FROM consumptions
WHERE id = :id;