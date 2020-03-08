(ns stonks.core
  (:require [compojure.core :refer [defroutes]]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring])

  (:gen-class))

(defroutes app-routes
  (GET "/" [] "Hello World"))

(def application
  (app-routes))

(defn -main
  []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (ring/run-jetty application {:port port :join? false})))
