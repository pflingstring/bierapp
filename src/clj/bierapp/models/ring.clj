(ns bierapp.models.ring
  (:require [bierapp.db.core :as db]
            ))

(defn add-ring
  [color price]
  (when (<= price 0)
    (throw (IllegalArgumentException. "Price cannot be negative or zero!")))

  (db/create-ring! {:color color
                    :price price}))

(defn get-price
  [color]
  (-> (db/get-ring-price {:color (name color)})
      (:price)))

(defn calculate-price
  [rings]
  (let [keys   (keys rings)
        prices (map get-price keys)]
    (->> (map rings keys)
         (map * prices)
         (reduce +))))