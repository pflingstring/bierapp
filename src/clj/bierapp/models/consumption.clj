(ns bierapp.models.consumption
  (:require [bierapp.db.core :as db]
            [bierapp.models.ring :as r]
            [bierapp.models.user :as u]
            [conman.core :as conman]))

(defn add-rings-to-user
  [user-id rings date]
  (conman/with-transaction [db/*db*]
    (let [amount (r/calculate-price rings)
          json-rings (db/to-pg-json rings)]
      (u/deduct-money! user-id amount)
      (db/create-consumption! {:user_id user-id
                               :date date
                               :rings json-rings}))))