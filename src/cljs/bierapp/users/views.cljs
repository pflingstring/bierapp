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

(defn create-rows
  [users]
  [ui/table-body {:display-row-checkbox false}
   (for [id (keys users)]
     (let [user (id users)]
       [ui/table-row (when (< (:balance user) 0)
                       {:style {:backgroundColor (color :deepOrange50)}})

        [ui/table-row-column [:a {:on-click #(accountant/navigate! (url-for :user-id :id id))}
          (:name user)]]
        [ui/table-row-column {:style {:text-align "right"}}
          (:balance user)]]))])

(defn user-table
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
       (create-rows balance)]]]))