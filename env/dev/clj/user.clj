(ns user
  (:require [mount.core :as mount]
            [bierapp.figwheel :refer [start-fw stop-fw cljs]]
            bierapp.core))

(defn start []
  (mount/start-without #'bierapp.core/repl-server))

(defn stop []
  (mount/stop-except #'bierapp.core/repl-server))

(defn restart []
  (stop)
  (start))


