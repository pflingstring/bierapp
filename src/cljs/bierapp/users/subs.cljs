(ns bierapp.users.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :users-balance
  (fn [db _]
    (:users-balance db)))