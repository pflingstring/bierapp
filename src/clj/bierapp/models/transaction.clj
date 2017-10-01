(ns bierapp.models.transaction
  (:require [bierapp.db.core :as db]
            ))

(defn create
  [from amount date]
  (db/create-transaction! {:from_id    from
                           :amount     amount
                           :date       date}))