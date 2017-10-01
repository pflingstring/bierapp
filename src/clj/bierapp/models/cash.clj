(ns bierapp.models.cash
  (:require
    [bierapp.db.core :as db]
    [bierapp.models.balance-log :as log]
    [bierapp.models.transaction :as t]))

(defn get-balance []
  (-> (db/get-user-balance {:id 1})
      (:balance)))

(defn update-balance!
  [amount]
  (db/update-user-balance! {:id 1
                            :balance amount}))

(defn deposit
  [amount transaction-id]
  (let [old-amount (get-balance)
        new-amount (+ old-amount amount)]
    (log/create-entry! 1 transaction-id old-amount new-amount)
    (update-balance! new-amount)))

(defn withdraw
  [amount date]
  (let [old-amount  (get-balance)
        new-amount  (- old-amount amount)
        transaction (-> (t/create 1 (- amount) date) (:id))]
    (log/create-entry! 1 transaction old-amount new-amount)
    (update-balance! new-amount)))