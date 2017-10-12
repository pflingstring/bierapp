(ns bierapp.handlers.transaction
  (:require
    [clojure.string :refer [capitalize join]]
    [bierapp.models.transaction :as m]
            ))

(defn format-rings
  [rings]
  (for [ring (keys rings)]
    (str (-> ring name capitalize) " " (ring rings))))

(defn get-user-transactions
  [id]
  (let [query (m/get-user-transactions (read-string id))
        trans (fn [m]
                (println (:rings m))
                {(:id m) {:rings      (if (nil? (:rings m))
                                        "Enizahlung"
                                        (join ", " (format-rings (:rings m))))
                          :amount     (:amount m)
                          :old-amount (:old_amount m)
                          :new-amount (:new_amount m)}})
        trans-map (map trans query)]
    (into {} trans-map)))