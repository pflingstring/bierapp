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

(reg-sub
  :all-users
  (fn [db _]
    (if (= (:name-input-src db) {})
      []
      (keys (:name-input-src db)))))

(reg-sub
  :upload-rings-button-status
  (fn [db _]
    (:upload-rings-button-disabled? db)))

(reg-sub
  :upload-rings-error
  (fn [db _]
    (:upload-rings-error db)))