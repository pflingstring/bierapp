(ns bierapp.money.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
            [ajax.core :as ajax]))

(reg-event-db
  :set-money-amount
  (fn [db [_ amount]]
    (assoc db :current-money-amount amount)))

(reg-event-db
  :clear-amount-input
  (fn [db _]
    (assoc db :current-money-amount "")))

(reg-event-db
  :disable-upload-money-button
  (fn [db _]
    (assoc db :upload-money-button-disabled? true)))

(reg-event-db
  :enable-upload-money-button
  (fn [db _]
    (assoc db :upload-money-button-disabled? false)))


(reg-event-fx
  :upload-money
  (fn [{db :db} _]
    {:http-xhrio {:method          :post
                  :uri             "/money/add"
                  :params          {:user-id (:current-user-id db)
                                    :amount  (:current-money-amount db)}
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:on-money-upload-ok]
                  :on-failure      [:on-money-upload-failure]}}))

(reg-event-db
  :on-money-upload-ok
  (fn [db _]
    (assoc db :upload-money-button-disabled? false)))

(reg-event-db
  :on-money-upload-failure
  (fn [db _]
    (assoc db :upload-money-button-disabled? false)))


(reg-event-fx
  :get-kasse-log
  (fn [{db :db} _]
    {:http-xhrio {:method          :get
                  :uri             "/get/kasse/log"
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:set-kasse-log]
                  :on-failure      [:on-money-upload-failure]}}))

(reg-event-db
  :set-kasse-log
  (fn [db [_ log]]
    (assoc db :kasse-log log)))