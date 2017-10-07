(ns bierapp.handlers.consumption
  (:require [bierapp.models.consumption :as m]
            ))

(defn create-consumption
  [id rings]
  (m/add-rings-to-user id rings "20170101"))

(defn create-bulk-consumption
  [rings-map]
  (doseq [key (keys rings-map)]
    (create-consumption (-> key
                            name
                            read-string)
                        (:rings (key rings-map)))))
