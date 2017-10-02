(ns bierapp.core
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.core :as ui]
    [cljs-react-material-ui.icons :as ic]

    [reagent.core :as r]
    [re-frame.core :as rf]

    [ajax.core :refer [GET POST]]
    [bierapp.ajax :refer [load-interceptors!]]
    [bierapp.events]

    [goog.events :as events]
    [goog.history.EventType :as HistoryEventType]
    [reagent.core :as reagent])
  (:import goog.History))

(defn navbar
  []
  (ui/mui-theme-provider
    {:mui-theme (ui/get-mui-theme)}

    (ui/app-bar
      {:title                         "bierapp"
       :on-left-icon-button-touch-tap #(rf/dispatch [:open-drawer])})))

(defn drawer
  []
  (ui/mui-theme-provider
    {:mui-theme (ui/get-mui-theme)}

    (ui/drawer
      {:docked            false
       :open              @(rf/subscribe [:drawer-status])
       :on-request-change #(rf/dispatch  [:close-drawer])}

      (ui/menu-item {:on-click #(rf/dispatch [:drawer-navigate :home])}
                    "Home")

      (ui/menu-item {:on-click #(rf/dispatch [:drawer-navigate :about])}
                    "About")

      (ui/menu-item {:on-click #(rf/dispatch [:drawer-navigate :users])}
                    "Users"))))

(defn home-page
  []
  [:div
   [:p "Welcome to the home page"]])

(defn about-page
   []
   [:div
    [:p "About Page"]])

(defn user-page
  []
  [:div "Users"])

(def pages
  {:home  #'home-page
   :about #'about-page
   :users #'user-page})

(defn complete-page []
  [:div
   [drawer]
   [navbar]
   [(pages @(rf/subscribe [:page]))]])

;; -------------------------
;; Initialize app

(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'complete-page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:initialize-db])
  (load-interceptors!)
  (mount-components))
