(ns bierapp.events
  (:require [bierapp.db :as db]
            [bierapp.routes :as routes]
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
    (routes/accountant-navigate! page)
    (dispatch [:close-drawer])
    (dispatch [:set-active-page page])))

;; ---------------
;; Add rings event
;; ---------------

(reg-event-db
  :add-consumption-entry
  (fn [db [_ name]]
    (assoc-in db [:rings (:current-position db)] {:name  name
                                                  :rings {}})))

(reg-event-db
  :inc-position
  (fn [db [_ name]]
    (update db :current-user inc)))

(reg-event-db
  :add-ring-color
  (fn [db [_ color]]
    (dispatch [:set-current-ring-color (-> color keys first)])
    (let [path [:rings (:current-position db) :rings]
          current-rings (get-in db path)]
      (assoc-in db path (merge current-rings
                               color)))))

(reg-event-db
  :set-current-ring-color
  (fn [db [_ color]]
    (assoc db :current-ring-color color)))

(reg-event-db
  :add-ring-number
  (fn [db [_ nr]]
    (let [path [:rings (:current-position db) :rings (:current-ring-color db)]]
      (assoc-in db path nr))))

(reg-event-db
  :clear-ring-nr-input
  (fn [db _]
    (assoc db :ring-number-input "")))

(reg-event-db
  :clear-ring-color-input
  (fn [db _]
    (assoc db :ring-color-input "")))

(reg-event-db
  :set-ring-color-input
  (fn [db [_ color]]
    (assoc db :ring-color-input color)))

(reg-event-db
  :set-ring-nr-input
  (fn [db [_ nr]]
    (assoc db :ring-number-input nr)))

;;subscriptions

(reg-sub
  :current-rings
  (fn [db _]
    (get-in db [:rings (:current-position db) :rings])))

(reg-sub
  :ring-color-input
  (fn [db _]
    (:ring-color-input db)))

(reg-sub
  :ring-number-input
  (fn [db _]
    (:ring-number-input db)))

(reg-sub
  :current-position
  (fn [db _]
    (:current-position db)))

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