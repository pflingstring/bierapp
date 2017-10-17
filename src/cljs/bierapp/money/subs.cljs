(ns bierapp.money.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :amount-input
  (fn [db _]
    (:current-money-amount db)))

(reg-sub
  :upload-money-button-status
  (fn [db _]
    (:upload-money-button-disabled? db)))

(reg-sub
  :kasse-log
  (fn [db _]
    (:kasse-log db)))