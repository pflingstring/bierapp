(ns bierapp.core
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.core :refer [get-mui-theme]]
    [cljs-react-material-ui.reagent :as ui]
    [re-frame.core :as rf]
    [reagent.core  :as r]
    [bierapp.events]
    [bierapp.routes :refer [url-for hook-browser-navigation!]]
    [bierapp.ajax   :refer [load-interceptors!]]
    [bierapp.rings.views :refer [add-rings-view]]))

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

      [ui/menu-item {:on-click #(rf/dispatch [:drawer-navigate :home])}
                    "Home"]]])

(defn home-page
  []
  [:div {:style {:backgroundColor "#E3F2FD"}}
   [add-rings-view]])

(def pages
  {:home  #'home-page
  })

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
  (rf/dispatch-sync [:get-users])
  (load-interceptors!)
  (hook-browser-navigation!)
  (mount-components))
