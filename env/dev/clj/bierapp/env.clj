(ns bierapp.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [bierapp.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[bierapp started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[bierapp has shut down successfully]=-"))
   :middleware wrap-dev})
