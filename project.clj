(defproject org.bodil/winning-the-war "0.1.0-SNAPSHOT"
  :description "Code for the talk \"Winning the War on JS\""
  :url "https://github.com/bodil/winning-the-war"
  :license {:name "Apache License, version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.bodil/redlobster "0.2.0"]
                 [org.bodil/dogfort "0.1.0-SNAPSHOT"]
                 [org.bodil/pylon "0.3.0"]
                 [org.bodil/error "0.1.2"]
                 [hiccups "0.1.1"]]
  :node-dependencies [[mongodb "1.2.14"]]
  :plugins [[lein-cljsbuild "0.3.0"]
            [lein-npm "0.1.0"]
            [org.bodil/lein-noderepl "0.1.9"]
            [org.bodil/lein-error "0.1.2"]]
  :cljsbuild {:builds
              [{:source-paths ["src"],
               :compiler
               {:output-dir "js",
                :target :nodejs,
                :output-to "js/main.js",
                :optimizations :simple}}]}
  :main "js/main.js")
