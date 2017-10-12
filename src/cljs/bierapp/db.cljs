(ns bierapp.db)

(def default-db
  {:page                          {:curr :home :args []}
   :rings                         {}
   :users-balance                 {}
   :current-user-id               -1
   :current-name-input            ""
   :current-ring-color            ""
   :current-money-amount          ""
   :ring-color-input              ""
   :ring-number-input             ""
   :name-input-src                {}
   :upload-rings-button-disabled? false
   :upload-money-button-disabled? false
   :upload-rings-error            ""
   :drawer-opened?                false})

