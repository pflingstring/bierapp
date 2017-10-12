(ns bierapp.users.views
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.reagent :as ui]
    [cljs-react-material-ui.core :refer [get-mui-theme color]]
    [re-frame.core :as rf]
    [bierapp.routes :refer [url-for]]
    [bierapp.users.events]
    [bierapp.users.subs]
    [accountant.core :as accountant]))

(defn- create-balance-rows
  [users]
  [ui/table-body {:display-row-checkbox false}
   (for [id (keys users)]
     (let [user (id users)]
       [ui/table-row (when (< (:balance user) 0)
                       {:style {:backgroundColor (color :deepOrange50)}})

        [ui/table-row-column [:a {:on-click #(do (rf/dispatch [:set-path-args [:id id]])
                                                 (rf/dispatch [:get-user-transactions id])
                                                 (rf/dispatch [:navigate-to :user-id]))}
          (:name user)]]
        [ui/table-row-column {:style {:text-align "right"}}
          (:balance user)]]))])

(defn user-balance-table
  []
  (let [balance @(rf/subscribe [:users-balance])]
    [ui/mui-theme-provider {:mui-theme (get-mui-theme)}

     [ui/paper {:style {:width 600}}
      [ui/table {:selectable false}
       [ui/table-header {:adjust-for-checkbox false
                         :display-select-all  false}
        [ui/table-row
         [ui/table-header-column "Name"]
         [ui/table-header-column
          {:style {:text-align "right"}}
          "Amount"]]]
       (create-balance-rows balance)]]]))

(defn user-transaction-rows
  [transactions-map]
  [ui/table-body {:display-row-checkbox false}
   (for [id (keys transactions-map)]
     (let [transaction (get transactions-map id)]
       [ui/table-row
        [ui/table-row-column {:style {:white-space "normal"
                                      :word-wrap   "break-word"}}
         (:rings transaction)]
         [ui/table-row-column {:style {:text-align "right"}} (:amount transaction)]
         [ui/table-row-column {:style {:text-align "right"}} (:old-amount transaction)]
         [ui/table-row-column {:style {:text-align "right"}} (:new-amount transaction)]]))])

(defn user-transactions-table
  []
  (let [transactions @(rf/subscribe [:user-transactions])]
    [:div
     [ui/mui-theme-provider {:mui-theme (get-mui-theme)}
      [ui/paper {:style {:width 650}}
       [ui/table {:selectable false}
        [ui/table-header {:adjust-for-checkbox false
                          :display-select-all  false}
         [ui/table-row
          [ui/table-header-column "Description"]
          [ui/table-header-column {:style {:text-align "right"}} "Total"]
          [ui/table-header-column {:style {:text-align "right"}} "Old amount"]
          [ui/table-header-column {:style {:text-align "right"}} "New amount"]]]
        (user-transaction-rows transactions)]]]]))