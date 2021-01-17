(ns stonks.core
  (:gen-class)
  (:require [compojure.core :refer [defroutes]]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [stonks.routes.static :as static]
            [stonks.routes.stocks :as stocks]
            [stonks.views.static :refer [not-found]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-response]]))

(defroutes app-routes
  static/routes
  stocks/routes
  (route/resources "/")
  (route/not-found (not-found)))

(def application
  (-> (wrap-defaults app-routes site-defaults)
      wrap-json-response))

(defn app-server-start
  [port]
  (jetty/run-jetty #'application {:port port :join? false}))

(defn -main
  [& [port]]
  (let [port (Integer. (or port (System/getenv "PORT")))]
    (app-server-start port)))

(comment
  (def app-server-instance (-main 8080))
  (.stop app-server-instance)
  ,)
