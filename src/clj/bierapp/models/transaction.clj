(ns bierapp.models.transaction
  (:require [bierapp.db.core :as db]
            ))

(defn create
  [from amount date]
  (db/create-transaction! {:from_id    from
                           :amount     amount
                           :date       date}))

(defn get-by-id
  [id]
  (db/get-transaction {:id id}))

(defn update-amount
  [id amount]
  (db/update-transaction-amount! {:id id
                                  :amount amount}))