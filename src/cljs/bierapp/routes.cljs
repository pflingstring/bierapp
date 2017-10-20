(ns bierapp.routes
  (:require [bidi.bidi :as bidi]
            [accountant.core :as accountant]
            [re-frame.core :as rf]))

(def app-routes
  ["/" {""      :home
        "money" :money
        "kasse" :kasse
        "users/" {"" :users
                 [:id] :user-id}}])

(def url-for (partial bidi/path-for app-routes))

(defn hook-browser-navigation!
  []
  (accountant/configure-navigation!
    {:nav-handler  (fn [path]
                     (let [page (-> (bidi/match-route app-routes path) (:handler))]
                       (rf/dispatch [:set-active-page page])))

     :path-exists? (fn [path]
                     (boolean (bidi/match-route app-routes path)))})
  (accountant/dispatch-current!))

(defn accountant-navigate!
  [path]
  (let [args @(rf/subscribe [:path-args])]
    (cond
      (= path :user-id) (accountant/navigate! (url-for path (first args) (second args))))
    (accountant/navigate! (url-for path))))