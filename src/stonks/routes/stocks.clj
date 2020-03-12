(ns stonks.routes.stocks
  (:require [compojure.core :refer [defroutes GET]]))

(defroutes routes
  (GET "/s/:symbol" [symbol] (str "<h1>" symbol "</h1>")))
