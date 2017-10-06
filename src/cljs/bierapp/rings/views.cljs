(ns bierapp.rings.views
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.reagent :as ui]
    [cljs-react-material-ui.core :refer [get-mui-theme]]
    [re-frame.core :as rf]
    [bierapp.rings.events]
    [bierapp.rings.subs]))

(def colors [:white :gold :brown :pink])
(def search-filter (aget js/MaterialUI "AutoComplete" "caseInsensitiveFilter"))

(defn- create-table-rows
  []
  (let [rings @(rf/subscribe [:current-rings])
        keys (keys rings)]
    (for [k keys]
      (let [current-map (get rings k)
            current-rings (:rings current-map)]
        [ui/table-row
         [ui/table-row-column (:name current-map)]
         (for [color colors]
           (if (contains? current-rings color)
             [ui/table-row-column (color current-rings)]
             [ui/table-row-column 0]))]))))

(defn- select-name
  []
  [ui/mui-theme-provider {:mui-theme (get-mui-theme)}
   [ui/auto-complete
    {:hint-text      "Name"
     :id             "nameSelector"
     :full-width     true
     :filter         search-filter
     :search-text    @(rf/subscribe [:name-input])
     :dataSource     @(rf/subscribe [:all-users])
     :on-new-request (fn [name _]
                       (rf/dispatch [:set-current-user-id name])
                       (rf/dispatch [:add-consumption-entry name])
                       (rf/dispatch [:clear-ring-color-input])
                       (.focus (.getElementById js/document "ringColor")))}]])

(defn- select-ring-color
  []
  [ui/mui-theme-provider {:mui-theme (get-mui-theme)}
   [ui/auto-complete
    {:id             "ringColor"
     :hint-text      "Ring"
     :full-width     true
     :filter         search-filter
     :search-text    @(rf/subscribe [:ring-color-input])
     :dataSource     ["white" "gold" "brown" "pink"]
     :on-new-request (fn [color _]
                       (if (= color "next")
                         (do (rf/dispatch [:clear-name-input])
                             (rf/dispatch [:inc-position])
                             (rf/dispatch [:set-ring-color-input "-"])
                             ;(create-table-rows)
                             (.focus (.getElementById js/document "nameSelector")))
                         (do
                           (rf/dispatch [:add-ring-color {(keyword color) 0}])
                           (rf/dispatch [:set-ring-color-input color])
                           (.focus (.getElementById js/document "ringNr")))))}]])

(defn- select-ring-nr
  []
  [ui/mui-theme-provider {:mui-theme (get-mui-theme)}
   [ui/auto-complete
    {:hint-text       "nr."
     :id              "ringNr"
     :full-width      true
     :search-text     @(rf/subscribe [:ring-number-input])
     :dataSource      []
     :on-update-input (fn [str _ _]
                        (rf/dispatch [:set-ring-nr-input str]))
     :on-new-request  (fn [nr _]
                        (rf/dispatch [:clear-ring-color-input])
                        (rf/dispatch [:clear-ring-nr-input])
                        (rf/dispatch [:add-ring-number nr])
                        (.focus (.getElementById js/document "ringColor")))}]])

(defn- add-rings-input
  []
  [:div {:style {:padding 10}}
   [ui/mui-theme-provider
    {:mui-theme (get-mui-theme)}

    [ui/paper {:style {:width 250}}
     [select-name]
     [:div
      [:div {:style {:width 200 :display "inline-block"}}
       [select-ring-color]]
      [:div {:style {:width 50 :display "inline-block"}}
       [select-ring-nr]]]]]])

;; TODO: add unique key for each row
;; see https://reactjs.org/docs/lists-and-keys.html#keys
(defn- rings-table
  []
  (let [rings @(rf/subscribe [:current-rings])]
    [:div {:style {:width   800
                   :padding 10}}
     [ui/mui-theme-provider {:mui-theme (get-mui-theme)}
      [ui/paper
       [ui/table
        [ui/table-header
         [ui/table-row
          [ui/table-header-column "Name"]
          [ui/table-header-column "White"]
          [ui/table-header-column "Gold"]
          [ui/table-header-column "Brown"]
          [ui/table-header-column "Pink"]]]
        [ui/table-body
         (create-table-rows)]]]]]))

(defn add-rings-view
  []
  [:div
   [add-rings-input]
   [rings-table]])
