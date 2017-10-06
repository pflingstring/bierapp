(ns bierapp.handlers.user
  (:require [bierapp.models.user :as m]
            ))

(defn get-user-ids
  []
  (let [all-users (m/get-all-users)
        single-user (fn [user]
                      {(:id user)
                       (str (:first_name user) " " (:last_name user))})
        map-of-users (map single-user all-users)]
    (into {} map-of-users)))