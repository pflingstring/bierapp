(ns bierapp.events
  (:require [bierapp.db :as db]
            [bierapp.routes :as routes]
            [re-frame.core :refer [dispatch reg-event-db reg-sub]]))

;; --------------------------
;; Init und navigation events
;; --------------------------

(reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))

(reg-event-db
  :set-active-page
  (fn [db [_ page]]
    (assoc db :page page)))

;; --------------
;; Drawer events
;; --------------

(reg-event-db
  :open-drawer
  (fn [db _]
    (assoc db :drawer-opened? true)))

(reg-event-db
  :close-drawer
  (fn [db _]
    (assoc db :drawer-opened? false)))

(reg-event-db
  :drawer-navigate
  (fn [db [_ page]]
    (routes/accountant-navigate! page)
    (dispatch [:close-drawer])
    (dispatch [:set-active-page page])))


;; ---------------------
;; General subscriptions
;; ---------------------

(reg-sub
  :page
  (fn [db _]
    (:page db)))

(reg-sub
  :drawer-status
  (fn [db _]
    (:drawer-opened? db)))

