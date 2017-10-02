(ns bierapp.events
  (:require [bierapp.db :as db]
            [re-frame.core :refer [dispatch reg-event-db reg-sub]]))

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
    (dispatch [:close-drawer])
    (dispatch [:set-active-page page])))


;;subscriptions

(reg-sub
  :page
  (fn [db _]
    (:page db)))

(reg-sub
  :docs
  (fn [db _]
    (:docs db)))

(reg-sub
  :drawer-status
  (fn [db _]
    (:drawer-opened? db)))