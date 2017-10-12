(ns bierapp.users.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
            [clojure.reader :refer [read-string]]
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

(reg-event-fx
  :get-user-transactions
  (fn [{db :db} [_ id]]
    {:http-xhrio {:method          :get
                  :uri             (str "/users/" (name id) "/transactions")
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:set-user-transactions]
                  :on-failure      [:get-balance-error]}}))

(defn sort-transactions
  [transactions]
  (let [int-id (for [id (keys transactions)]
                 {(-> id name read-string) (id transactions)})]
    (into (sorted-map) int-id)))

(reg-event-db
  :set-user-transactions
  (fn [db [_ transactions]]
    (let [sorted ()]
      (assoc db :transactions (sort-transactions transactions)))))