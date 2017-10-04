(ns bierapp.rings.events
  (:require [re-frame.core :refer [dispatch reg-event-db]]))


;; ----------------
;; Add rings events
;; ----------------

(reg-event-db
  :add-consumption-entry
  (fn [db [_ name]]
    (let [path [:rings (:current-position db)]]
      (dispatch [:set-current-name-input name])
      (assoc-in db path {:name  name
                         :rings {}}))))

(reg-event-db
  :add-ring-color
  (fn [db [_ color]]
    (dispatch [:set-current-ring-color (-> color keys first)])
    (let [path [:rings (:current-position db) :rings]
          current-rings (get-in db path)]
      (assoc-in db path (merge current-rings
                               color)))))

(reg-event-db
  :add-ring-number
  (fn [db [_ nr]]
    (let [path [:rings (:current-position db) :rings (:current-ring-color db)]]
      (assoc-in db path nr))))

;; ------------------
;; Input manipulation
;; ------------------
(reg-event-db
  :set-ring-color-input
  (fn [db [_ color]]
    (assoc db :ring-color-input color)))

(reg-event-db
  :set-ring-nr-input
  (fn [db [_ nr]]
    (assoc db :ring-number-input nr)))

(reg-event-db
  :clear-ring-color-input
  (fn [db _]
    (assoc db :ring-color-input "")))

(reg-event-db
  :clear-ring-nr-input
  (fn [db _]
    (assoc db :ring-number-input "")))

(reg-event-db
  :clear-name-input
  (fn [db _]
    (assoc db :current-name-input "")))

;; -------------------
;; app-db manipulation
;; -------------------

(reg-event-db
  :set-current-ring-color
  (fn [db [_ color]]
    (assoc db :current-ring-color color)))

(reg-event-db
  :set-current-name-input
  (fn [db [_ name]]
    (assoc db :current-name-input name)))

(reg-event-db
  :inc-position
  (fn [db [_ name]]
    (update db :current-position inc)))