(ns bierapp.models.user
  (:require [bierapp.db.core :as db :refer [*db*]]
            [bierapp.config :as config]
            [bierapp.models.cash :as cash]
            [bierapp.models.transaction :as transaction]
            [bierapp.models.balance-log :as log]
            [conman.core :as conman]))

(defn create
  [fname lname email pass balance]
  (db/create-user! {:first_name fname
                    :last_name  lname
                    :email      email
                    :pass       pass
                    :balance    balance}))

(defn get-all-users
  []
  (db/get-all-users))

(defn get-users-balance
  []
  (db/get-balances))

(defn get-balance
  [id]
  (-> (db/get-user-balance {:id id})
      (:balance)))

(defn update-balance-with!
  [id balance]
  (db/update-user-balance! {:id      id
                            :balance balance}))

(defn update-balance-by!
  [id amount]
  (let [old-balance (get-balance id)
        new-balance (+ old-balance amount)]
    (update-balance-with! id new-balance)))

(defn deposit-money!
  [user-id amount date]
  (when (<= amount 0)
    (throw (IllegalArgumentException. "Amount cannot be negative or zero!")))

  (conman/with-transaction [*db*]
    (let [old-balance (get-balance user-id)
          new-balance (+ old-balance amount)
          transaction (-> (transaction/create user-id amount date) (:id))]
      (cash/deposit amount transaction)
      (log/create-entry! user-id transaction old-balance new-balance)
      (update-balance-with! user-id new-balance))))

(defn deduct-money!
  [user-id amount]
  (when (<= amount 0)
    (throw (IllegalArgumentException. "Amount cannot be negative or zero!")))

  (let [old-balance (get-balance user-id)
        new-balance (- old-balance amount)]
    (update-balance-with! user-id new-balance)))