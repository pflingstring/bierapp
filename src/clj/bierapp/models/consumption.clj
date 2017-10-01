(ns bierapp.models.consumption
  (:require [bierapp.db.core :as db]
            [bierapp.models.ring :as r]
            [bierapp.models.user :as u]
            [bierapp.models.transaction :as t]
            [bierapp.models.balance-log :as log]
            [conman.core :as conman]))

(defn create
  [user-id rings transaction-id date]
  (db/create-consumption! {:user_id        user-id
                           :rings          (db/to-pg-json rings)
                           :transaction_id transaction-id
                           :date           date}))

(defn add-rings-to-user
  [user-id rings date]
  (conman/with-transaction [db/*db*]
    (let [rings-price (r/calculate-price rings)
          old-balance (u/get-balance user-id)
          new-balance (- old-balance rings-price)
          transaction (-> (t/create user-id (- rings-price) date) (:id))]
      (u/deduct-money! user-id rings-price)
      (log/create-entry! user-id transaction old-balance new-balance)
      (create user-id rings transaction date))))