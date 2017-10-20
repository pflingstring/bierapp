(ns bierapp.events
  (:require [bierapp.db :as db]
            [bierapp.routes :as routes]
            [re-frame.core :refer [dispatch reg-event-db reg-event-fx reg-sub reg-fx]]))

;; --------------------------
;; Init und navigation events
;; --------------------------

(reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))

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

(reg-event-fx
  :drawer-navigate
  (fn [{db :db} [_ page]]
    {:dispatch-n      (list [:close-drawer] [:clear-page-args])
     :router-navigate page}))

;; ----------
;; Navigation
;; ----------

(reg-event-db
  :set-active-page
  (fn [db [_ page]]
    (assoc-in db [:page :curr] page)))

(reg-event-db
  :set-path-args
  (fn [db [_ args]]
    (assoc-in db [:page :args] args)))

(reg-event-db
  :clear-page-args
  (fn [db _]
    (assoc-in db [:page :args] [])))

(reg-fx
  :router-navigate
  (fn [page]
    (routes/accountant-navigate! page)))

(reg-event-fx
  :navigate-to
  (fn [_ [_ page]]
    {:router-navigate page}))


;; ---------------------
;; General subscriptions
;; ---------------------

(reg-sub
  :page
  (fn [db _]
    (:curr (:page db))))

(reg-sub
  :drawer-status
  (fn [db _]
    (:drawer-opened? db)))

(reg-sub
  :path-args
  (fn [db _]
    (get-in db [:page :args])))
