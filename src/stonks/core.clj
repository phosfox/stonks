(ns stonks.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring])

  (:gen-class))

(defroutes app-routes
  (GET "/" [] "<h1>Hello World</h1>")
  (route/not-found "<h1>Page not found</h1>"))

(def application
  app-routes)

(defn -main
  []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (ring/run-jetty application {:port port
                                 :join? false})))
