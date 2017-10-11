(ns bierapp.db)

(def default-db
  {:page                          :home
   :rings                         {}
   :current-user-id               -1
   :current-name-input            ""
   :current-ring-color            ""
   :current-money-amount          0
   :ring-color-input              ""
   :ring-number-input             ""
   :name-input-src                {}
   :upload-rings-button-disabled? false
   :upload-money-button-disabled? false
   :upload-rings-error            ""
   :drawer-opened?                false})

