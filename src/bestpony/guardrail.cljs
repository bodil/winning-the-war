(ns bestpony.guardrail
  (:require-macros [error.macros :refer [test is]]
                   [redlobster.macros :refer [let-realised]])
  (:require [error.test :refer [run-tests clear]]
            [redlobster.io :as io]
            [bestpony.clazz :refer [RainbowDash]]))

(test "Rainbow Dash should be a pegasus pony"
  (is (= "pegasus pony" (.ponyType (RainbowDash.))))
  (done))

(test {:only :node} "Rainbow Dash should be best pony"
  (let-realised [best-pony (io/slurp "bestpony.txt")]
    (is (= "Rainbow Dash" @best-pony))
    (done)))

(test {:only :node :expect :fail} "Fluttershy should not be best pony"
  (let-realised [best-pony (io/slurp "bestpony.txt")]
    (is (= "Fluttershy" @best-pony))
    (done)))
