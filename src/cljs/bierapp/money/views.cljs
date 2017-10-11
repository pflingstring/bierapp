(ns bierapp.money.views
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.reagent :as ui]
    [cljs-react-material-ui.core :refer [get-mui-theme]]
    [cljs.reader :refer [read-string]]
    [re-frame.core :as rf]
    [bierapp.money.events]
    [bierapp.money.subs]))

(def search-filter (aget js/MaterialUI "AutoComplete" "caseInsensitiveFilter"))

(defn add-money-view
  []
  [ui/mui-theme-provider {:mui-theme (get-mui-theme)}
   [ui/paper {:style {:width 300
                      :padding-left  5
                      :padding-right 5}}
    [ui/auto-complete
     {:hint-text      "Name"
      :full-width     true
      :filter         search-filter
      :search-text    @(rf/subscribe [:name-input])
      :dataSource     @(rf/subscribe [:all-users])
      :on-new-request (fn [name _]
                        (rf/dispatch [:set-current-name-input name])
                        (rf/dispatch [:set-current-user-id name])
                        (.focus (.getElementById js/document "amount")))}]

    [ui/auto-complete
     {:hint-text       "Amount"
      :id              "amount"
      :full-width      true
      :filter          search-filter
      :search-text     @(rf/subscribe [:amount-input])
      :dataSource      []
      :on-update-input (fn [amount _ _]
                         (rf/dispatch [:set-money-amount (read-string amount)]))}]

    [ui/raised-button
     {:label    "OK"
      :style {:margin-left 5
              :margin-bottom 5}
      :primary  true
      :disabled @(rf/subscribe [:upload-money-button-status])
      :on-click (fn [event]
                  (.preventDefault event)    ;; needed to prevent a bug
                                             ;; see https://github.com/callemall/material-ui/issues/8413
                  (rf/dispatch-sync [:upload-money])
                  (rf/dispatch [:disable-upload-money-button])
                  (rf/dispatch [:clear-name-input])
                  (rf/dispatch [:clear-amount-input]))}]]])