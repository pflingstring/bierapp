(ns bierapp.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[bierapp started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[bierapp has shut down successfully]=-"))
   :middleware identity})
