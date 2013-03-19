(ns bestpony.catfort
  (:use-macros [redlobster.macros :only [promise defer-node defer
                                         when-realised let-realised]]
               [dogfort.middleware.routes-macros :only [defroutes GET POST]])
  (:require-macros [cljs.node-macros :as n]
                   [hiccups.core :as hiccups])
  (:use [dogfort.http :only [run-http]]
        [dogfort.middleware.file :only [wrap-file]]
        [dogfort.middleware.body-parser :only [wrap-body-parser]]
        [cljs.node :only [log]])
  (:require [cljs.nodejs]
            [redlobster.promise :as p]
            [redlobster.mongo :as mongo]
            [dogfort.middleware.routes]
            [dogfort.util.response :as response]
            [hiccups.runtime]))

(n/require "fs" fs)

(def coll
  (let-realised [db (mongo/connect "localhost" 27017 "dogfort")]
    (mongo/collection @db "items")))

(defn list-item [item]
  [:li {:class (if (item "done") "done" "open")}
   [:form {:method "POST" :action (str "/check/" (item "_id"))}
    [:input.check {:type "submit" :value (if (item "done") "\u2611" "\u2610")}]]
   [:form {:method "POST" :action (str "/delete/" (item "_id"))}
    [:input.delete {:type "submit" :value "x"}]]
   [:span.todo (item "name")]])

(defn page-template [items]
  (hiccups/html
   [:html
    [:head
     [:link {:rel "stylesheet" :href "/screen.css"}]]
    [:body
     [:h1 [:img {:src "/sergeant.jpg"}] "Cat Fort Assault Plan"]
     [:ul (map list-item items)]
     [:form {:method "POST" :action "/new"}
      [:input {:type "text" :name "new"}]]]]))

(defroutes handler
  (GET "/" []
       (when-realised [coll]
         (let-realised [docs (mongo/find-all @coll {})]
           (response/response 200 (page-template @docs)))))

  (POST "/new" [new]
        (when-realised [coll]
          (let-realised [docs (mongo/save! @coll {"name" new "done" false})]
            (response/redirect-after-post "/"))))

  (POST "/delete/:id" [id]
        (when-realised [coll]
          (let-realised [docs (mongo/delete-id! @coll id)]
            (response/redirect-after-post "/"))))

  (POST "/check/:id" [id]
        (when-realised [coll]
          (let-realised
            [docs (mongo/update-id! @coll id #(assoc % "done" (not (% "done"))))]
            (response/redirect-after-post "/")))))

(defn main [& args]
  (run-http (-> handler
                (wrap-file "static")
                (wrap-body-parser))
      {:port 1336}))

(set! *main-cli-fn* main)
