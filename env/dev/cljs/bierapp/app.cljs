(ns ^:figwheel-no-load bierapp.app
  (:require [bierapp.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)
