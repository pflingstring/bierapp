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
    [bierapp.users.views :refer [user-table]]
    [bierapp.rings.views :refer [add-rings-view]]
    [bierapp.money.views :refer [add-money-view]]))

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
                    :on-click    #(rf/dispatch [:drawer-navigate :users])}
                    "Users"]
     [ui/list-item {:hover-color (color :blue100)
                    :on-click    #(rf/dispatch [:drawer-navigate :money])}
                    "Money"]]])

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
   [user-table]])

(defn user-id
  []
  (let [id (second @(rf/subscribe [:path-args]))]
    [:p "Hello " id]))

(def pages
  {:home    #'home-page
   :money   #'money-page
   :users   #'users-page
   :user-id #'user-id
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
  (rf/dispatch [:get-users-balance])
  (load-interceptors!)
  (hook-browser-navigation!)
  (mount-components))
