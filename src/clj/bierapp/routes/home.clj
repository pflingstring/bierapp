(ns bierapp.routes.home
  (:require [bierapp.layout :as layout]
            [bierapp.models.cash :as cash]
            [bierapp.handlers.user :as u]
            [bierapp.handlers.consumption :as c]
            [bierapp.handlers.transaction :as t]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [bierapp.db.core :as db]
            [cheshire.core :as json]))

(defn home-page []
  (layout/render "home.html"))

(defn to-json
  [string]
  (json/generate-string string))

(defn create-response
  "json should be a map
  response-type should be one response
  from ring.util.http-request"
  [response-type json]
  (ring.util.response/content-type
    (response-type (to-json json))
    "application/json"))

(defroutes home-routes
  (GET "/" []
    (home-page))

  (GET "/get" [] (create-response response/ok (u/get-user-ids)))
  (GET "/get/kasse/log" [] (create-response response/ok (cash/get-kasse-log)))
  (GET "/users/balance" [] (create-response response/ok (u/get-users-balance)))
  (GET "/users/:id/transactions" req (create-response response/ok (t/get-user-transactions (get-in req [:params :id]))))
  (POST "/rings/upload" req (create-response response/ok (c/create-bulk-consumption (:params req))))
  (POST "/money/add"    req (create-response response/ok (u/deposit-money (:params req))))

  (GET "/docs" []
    (-> (response/ok (-> "docs/docs.md" io/resource slurp))
        (response/header "Content-Type" "text/plain; charset=utf-8"))))

