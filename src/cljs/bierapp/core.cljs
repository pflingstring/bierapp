(ns bierapp.core
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.core :refer [get-mui-theme color]]
    [cljs-react-material-ui.reagent :as ui]
    [re-frame.core :as rf]
    [reagent.core  :as r]
    [bierapp.events]
    [bierapp.routes :refer [url-for hook-browser-navigation!]]
    [bierapp.ajax   :refer [load-interceptors!]]
    [bierapp.users.views :refer [user-balance-table user-transactions-table]]
    [bierapp.rings.views :refer [add-rings-view]]
    [bierapp.money.views :refer [add-money-view kasse-panel]]))

(defn navbar
  []
  [ui/mui-theme-provider {:mui-theme (get-mui-theme)}
   [ui/app-bar
    {:title                         "bierapp"
     :on-left-icon-button-touch-tap #(rf/dispatch [:open-drawer])}]])

(defn drawer
  []
  [ui/mui-theme-provider {:mui-theme (get-mui-theme)}
    [ui/drawer
      {:docked            false
       :open              @(rf/subscribe [:drawer-status])
       :on-request-change #(rf/dispatch  [:close-drawer])}

     [ui/list-item {:hover-color (color :blue100)
                    :on-click    #(rf/dispatch [:drawer-navigate :home])}
                    "Home"]
     [ui/list-item {:hover-color (color :blue100)
                    :on-click    (fn []
                                   (do (rf/dispatch [:get-users-balance])
                                       (rf/dispatch [:drawer-navigate :users])))}
                    "Users"]
     [ui/list-item {:hover-color (color :blue100)
                    :on-click    #(rf/dispatch [:drawer-navigate :money])}
      "Money"]
     [ui/list-item {:hover-color (color :blue100)
                    :on-click    (fn []
                                   (do (rf/dispatch [:drawer-navigate :kasse])
                                       (rf/dispatch [:get-kasse-log])))}
      "Kasse"]]])

(defn home-page
  []
  [:div
   [add-rings-view]])

(defn money-page
  []
  [:div {:style {:padding 10}}
   [add-money-view]])

(defn users-page
  []
  [:div {:style {:padding 10}}
   [user-balance-table]])

(defn kasse-page
  []
  [:div {:style {:padding 10}}
   [kasse-panel]])

(defn user-id
  []
  (let [id (second @(rf/subscribe [:path-args]))]
    [:div {:style {:padding 10}}
     [:h2 (str @(rf/subscribe [:user-name id]) "\t" @(rf/subscribe [:user-balance-by-id id]))]
     [user-transactions-table]]))

(def pages
  {:home    #'home-page
   :money   #'money-page
   :users   #'users-page
   :user-id #'user-id
   :kasse   #'kasse-page
  })

(defn complete-page []
  [:div
   [drawer]
   [navbar]
   [:div {:style {:backgroundColor "#E3F2FD"}}
    [(pages @(rf/subscribe [:page]))]]])

;; -------------------------
;; Initialize app

(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'complete-page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:initialize-db])
  (rf/dispatch-sync [:get-users])
  (load-interceptors!)
  (hook-browser-navigation!)
  (mount-components))
