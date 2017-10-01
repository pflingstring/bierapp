(ns bierapp.models.balance-log
  (:require [bierapp.db.core :as db]))

(defn create-entry!
  [from transaction old new]
  (db/create-log-entry! {:user_id        from
                         :transaction_id transaction
                         :old_amount     old
                         :new_amount     new}))

(defn get-by-transaction-id
  [id]
  (db/get-log-entry-by-transaction {:transaction_id id}))

(defn update-entry!
  [id new-amount]
  (db/update-log-entry! {:id id
                         :new_amount new-amount}))
