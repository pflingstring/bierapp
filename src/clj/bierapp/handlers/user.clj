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

(defn get-users-balance
  []
  (let [query (m/get-users-balance)
        balances (map (fn [user]
                        {(:id user) {:name    (str (:first_name user) " "
                                                   (:last_name user))
                                     :balance (:balance user)}})
                      query)]

    (dissoc (into {} balances) 1)))

(defn deposit-money
  [req]
  (m/deposit-money! (read-string (:user-id req))
                    (:amount req)
                    "20170101"))