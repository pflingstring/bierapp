(ns bierapp.users.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

(reg-event-fx
  :get-users-balance
  (fn [{db :db} _]
    {:http-xhrio {:method          :get
                  :uri             "/users/balance"
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:set-users-balance]
                  :on-failure      [:get-balance-error]}}))

(reg-event-db
  :set-users-balance
  (fn [db [_ balance]]
    (assoc db :users-balance balance)))