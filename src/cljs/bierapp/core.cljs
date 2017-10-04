(ns bierapp.core
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.core :refer [get-mui-theme]]
    [cljs-react-material-ui.reagent :as ui]
    [cljs-react-material-ui.icons :as ic]

    [reagent.core :as r]
    [re-frame.core :as rf]

    [bierapp.routes :refer [url-for hook-browser-navigation!]]
    [ajax.core :refer [GET POST]]
    [bierapp.ajax :refer [load-interceptors!]]
    [bierapp.events]

    [goog.events :as events]
    [goog.history.EventType :as HistoryEventType]
    [reagent.core :as reagent])
  (:import goog.History))

(defn navbar
  []
  [ui/mui-theme-provider
    {:mui-theme (get-mui-theme)}

    [ui/app-bar
      {:title                         "bierapp"
       :on-left-icon-button-touch-tap #(rf/dispatch [:open-drawer])}]])

(defn drawer
  []
  [ui/mui-theme-provider
   {:mui-theme (get-mui-theme)}

    [ui/drawer
      {:docked            false
       :open              @(rf/subscribe [:drawer-status])
       :on-request-change #(rf/dispatch  [:close-drawer])}

      [ui/menu-item {:on-click #(rf/dispatch [:drawer-navigate :home])}
                    "Home"]

      [ui/menu-item {:on-click #(rf/dispatch [:drawer-navigate :about])}
                    "About"]

      [ui/menu-item {:on-click #(rf/dispatch [:drawer-navigate :user])}
                    "Users"]]])

(defn select-name
  []
  [ui/mui-theme-provider
    {:mui-theme (get-mui-theme)}

    [ui/auto-complete
      {:hint-text            "Name"
       :search-text          @(rf/subscribe [:name-input])
       :full-width           true
       :dataSource           ["alb" "boa" "ceoa" "deva" "eva" "folia"]
       :filter               (aget js/MaterialUI "AutoComplete" "caseInsensitiveFilter")
       :on-new-request       (fn [name _]
                               (rf/dispatch [:add-consumption-entry name])
                               (rf/dispatch [:clear-ring-color-input])
                               (.focus (.getElementById js/document "ringColor")))
       :id                   "nameSelector"}]])

(defn select-ring-color
  []
  [ui/mui-theme-provider
    {:mui-theme (get-mui-theme)}

    [ui/auto-complete
      {:hint-text      "Ring color"
       :full-width     true
       :search-text    @(rf/subscribe [:ring-color-input])
       :dataSource     ["white" "gold" "brown" "pink"]
       :filter         (aget js/MaterialUI "AutoComplete" "caseInsensitiveFilter")
       :on-new-request (fn [color _]
                         (if (= color "next")
                           (do (rf/dispatch [:clear-name-input])
                               (rf/dispatch [:inc-position])
                               (rf/dispatch [:set-ring-color-input "-"])
                               (.focus (.getElementById js/document "nameSelector")))
                           (do
                             (rf/dispatch [:add-ring-color {(keyword color) 0}])
                             (rf/dispatch [:set-ring-color-input color])
                             (.focus (.getElementById js/document "ringNr")))))
       :id             "ringColor"}]])

(defn select-ring-nr
  []
  [ui/mui-theme-provider
    {:mui-theme (get-mui-theme)}

    [ui/auto-complete
     {:hint-text        "nr."
       :full-width      true
       :search-text     @(rf/subscribe [:ring-number-input])
       :dataSource      []
       :id              "ringNr"
       :on-update-input (fn [str _ _]
                          (rf/dispatch [:set-ring-nr-input str]))
       :on-new-request  (fn [nr _]
                          (rf/dispatch [:clear-ring-color-input])
                          (rf/dispatch [:clear-ring-nr-input])
                          (rf/dispatch [:add-ring-number nr])
                          (.focus (.getElementById js/document "ringColor")))}]])

(defn current-rings-for-user
  []
  (let [rings @(rf/subscribe [:current-rings])]
    [:div
     [:p (str rings)]]))

(defn add-rings
  []
  [ui/mui-theme-provider
   {:mui-theme (get-mui-theme)}

   [ui/paper {:zdepth 3 :style {:width 250}}
    [:div
     [select-name]
     [:div
      [:div {:style {:width 200 :display "inline-block"}}
       [select-ring-color]]
      [:div {:style {:width 50 :display "inline-block"}}
       [select-ring-nr]
       ]]]]])

(defn home-page
  []
  [:div
   [:h2 "Welcome to the bierapp"]
   [add-rings]
   [current-rings-for-user]])

(defn about-page
   []
   [:div
    [:p "About Page"]
    [:a {:href (url-for :home)} "Go to Home page"]])

(defn user-page
  []
  [:div
   [:p "Users"]
   [:a {:href (url-for :home)} "Go to Home page"]])

(def pages
  {:home  #'home-page
   :about #'about-page
   :user  #'user-page})

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
  (hook-browser-navigation!)
  (mount-components))
