(ns stonks.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [stonks.routes.static :as static]
            [stonks.routes.stocks :as stocks]
            [stonks.views.static :refer [not-found]]
            [ring.middleware.defaults :refer :all]))

(defroutes app-routes
  static/routes
  stocks/routes
  (route/resources "/")
  (route/not-found (not-found)))

(def application
  (wrap-defaults app-routes site-defaults))

(defn app-server-start
  [port]
  (jetty/run-jetty #'application {:port port :join? false}))

(defn -main
  [& [port]]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (app-server-start port)))

(comment
  (def app-server-instance (-main 8080))
  (.stop app-server-instance)
  ,)
