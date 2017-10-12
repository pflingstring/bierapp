(ns bierapp.users.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :users-balance
  (fn [db _]
    (:users-balance db)))

(reg-sub
  :user-name
  (fn [db [_ id]]
    (get-in db [:users-balance id :name])))

(reg-sub
  :user-balance-by-id
  (fn [db [_ id]]
    (get-in db [:users-balance id :balance])))

(reg-sub
  :user-transactions
  (fn [db [_ id]]
    (:transactions db)))