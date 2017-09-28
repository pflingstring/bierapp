-- :name create-ring! :! :n
-- :doc creates a new ring record
INSERT INTO rings
(color, price)
VALUES (:color, :price);

-- :name update-ring! :! :n
-- :doc update an existing ring record
UPDATE rings
SET color = :color, price = :price
WHERE id = :id;

-- :name get-ring :? :1
-- :doc retrieve a ring given the id.
SELECT * FROM rings
WHERE id = :id;

-- :name delete-ring! :! :n
-- :doc delete a ring given the id
DELETE FROM rings
WHERE id = :id;
