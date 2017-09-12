(ns bierapp.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [bierapp.core-test]))

(doo-tests 'bierapp.core-test)

