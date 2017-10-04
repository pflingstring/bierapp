(ns bierapp.rings.subs
  (:require [re-frame.core :refer [reg-sub]]))


;; -------------------
;; Input Subscriptions
;; -------------------

(reg-sub
  :name-input
  (fn [db _]
    (:current-name-input db)))

(reg-sub
  :ring-color-input
  (fn [db _]
    (:ring-color-input db)))

(reg-sub
  :ring-number-input
  (fn [db _]
    (:ring-number-input db)))

;; ------------------
;; Ring Subscriptions
;; ------------------

(reg-sub
  :current-rings
  (fn [db _]
    (:rings db)))

(reg-sub
  :current-position
  (fn [db _]
    (:current-position db)))
