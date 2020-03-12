(ns stonks.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [stonks.routes.static :as static]
            [stonks.routes.stocks :as stocks])

  (:gen-class))

(defroutes app-routes
  static/routes
  stocks/routes)


(def application
  (-> app-routes))

(defn -main
  []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (ring/run-jetty application {:port port
                                 :join? false})))
