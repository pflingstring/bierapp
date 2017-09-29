(ns bierapp.models.user
  (:require [bierapp.db.core :as db :refer [*db*]]
            [bierapp.config :as config]
            [bierapp.models.cashkeeper :refer [accept-payment]]
            [conman.core :as conman]))

(defn deposit-money!
  [user-id amount date]
  (when (<= amount 0)
    (throw (IllegalArgumentException. "Amount cannot be negative or zero!")))

  (conman/with-transaction [*db*]
    (let [old-balance (-> (db/get-user-balance {:id user-id})
                          (:balance))
          trn-id      (db/create-transaction!  {:from_id user-id
                                                :amount  amount
                                                :date    date})]
      (db/update-user-balance! {:id      user-id
                                :balance (+ old-balance amount)})

      (accept-payment amount (:id trn-id)))))

(defn deduct-money!
  [user-id amount]
  (when (<= amount 0)
    (throw (IllegalArgumentException. "Amount cannot be negative or zero!")))
  (db/update-user-balance! {:id user-id
                            :balance (- amount)}))