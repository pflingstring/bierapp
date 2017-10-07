(ns bierapp.routes.home
  (:require [bierapp.layout :as layout]
            [bierapp.handlers.user :as u]
            [bierapp.handlers.consumption :as c]
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
  (POST "/rings/upload" req (create-response response/ok (c/create-bulk-consumption (:params req))))

  (GET "/docs" []
    (-> (response/ok (-> "docs/docs.md" io/resource slurp))
        (response/header "Content-Type" "text/plain; charset=utf-8"))))

