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

(defn get-by-id
  [id]
  (db/get-consumption {:id id}))

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

(defn update-consumption-rings
  [id rings]
  (db/update-consumption-rings! {:id    id
                                 :rings (db/to-pg-json rings)}))

(defn update-rings
  [id rings]
  (let [old-entry (get-by-id id)
        old-price (r/calculate-price (:rings old-entry))
        new-price (r/calculate-price rings)
        trans-id  (:transaction_id old-entry)
        old-log   (log/get-by-transaction-id trans-id)]
    (conman/with-transaction [db/*db*]
      (update-consumption-rings id rings)
      (t/update-amount trans-id (- (Math/abs ^double new-price)))
      (log/update-entry! (:id old-log) (- (:old_amount old-log)
                                          new-price))
      (u/update-balance-by! (:user_id old-entry)
                            (- old-price new-price)))))