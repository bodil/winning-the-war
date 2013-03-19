(ns bestpony.clazz
  (:require [pylon.classes])
  (:use-macros [pylon.macros :only [defclass super]]))

(defclass Pony
  (constructor [name]
    (set! @.name name))
  (ponyType [] "pony")
  (hello []
    (str "Hello " @.name " the " (@.ponyType) "!")))

(defclass RainbowDash :extends Pony
  (constructor []
    (set! @.name "Rainbow Dash"))
  (ponyType [] "pegasus pony")
  (hello []
    (str (super) " You need to be about 20% cooler.")))
