(ns bierapp.models.cashkeeper
  (:require [bierapp.db.core :as db]))

(defn accept-payment
  [amount transaction-id]
  (let [old-cash (:balance (db/get-user-balance {:id 1}))
        current  (+ old-cash amount)]
    (db/update-user-balance! {:id 1
                              :balance current})
    (db/create-log-entry! {:transaction_id transaction-id
                           :amount current})))
