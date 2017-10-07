(ns bierapp.rings.events
  (:require [re-frame.core :refer [dispatch reg-event-db reg-event-fx]]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))


;; ----------------
;; Add rings events
;; ----------------

(reg-event-db
  :add-consumption-entry
  (fn [db [_ name]]
    (let [path [:rings (:current-user-id db)]]
      (dispatch [:set-current-name-input name])
      (assoc-in db path {:name  name
                         :rings {}}))))

(reg-event-db
  :add-ring-color
  (fn [db [_ color]]
    (dispatch [:set-current-ring-color (-> color keys first)])
    (let [path [:rings (:current-user-id db) :rings]
          current-rings (get-in db path)]
      (assoc-in db path (merge current-rings
                               color)))))

(reg-event-db
  :add-ring-number
  (fn [db [_ nr]]
    (let [path [:rings (:current-user-id db) :rings (:current-ring-color db)]]
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

(reg-event-db
  :disable-upload-rings-button
  (fn [db _]
    (assoc db :upload-rings-button-disabled? true)))

(reg-event-db
  :enable-upload-rings-button
  (fn [db _]
    (assoc db :upload-rings-button-disabled? false)))

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
  :set-current-user-id
  (fn [db [_ name]]
    (let [user-id ((:name-input-src db) name)]
      (assoc db :current-user-id user-id))))

(reg-event-fx
  :get-users
  (fn [{db :db} _]
    {:http-xhrio {:method          :get
                  :uri             "/get"
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:set-name-input-source]
                  :on-failure      [:get-users-error]}}))

(reg-event-db
  :set-name-input-source
  (fn [db [_ result]]
    (assoc db :name-input-src (clojure.set/map-invert result))))

(reg-event-db
  :get-users-error
  (fn [db [_ result]]
    (assoc db :name-input-src {:error "big error"})))

(reg-event-fx
  :upload-rings
  (fn [{db :db} _]
    {:http-xhrio {:method          :post
                  :uri             "/rings/upload"
                  :params          (:rings db)
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:clear-current-rings]
                  :on-failure      [:upload-rings-error]}}))

(reg-event-db
  :clear-current-rings
  (fn [db _]
    (dispatch [:enable-upload-rings-button])
    (dispatch [:clear-upload-error])
    (assoc db :rings {})))

(reg-event-db
  :clear-upload-error
  (fn [db _]
    (assoc db :upload-rings-error " ")))

(reg-event-db
  :upload-rings-error
  (fn [db [_ error]]
    ;(dispatch [:enable-upload-rings-button])
    (assoc db :upload-rings-error (str "Error uploading rings\n"
                                       (subs 0 300 error)))))