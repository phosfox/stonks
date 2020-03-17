(ns stonks.routes.stocks
  (:require [compojure.core :refer [defroutes GET]]
            [stonks.views.stocks :as stocks]
            [stonks.client :as client]))

(defroutes routes
  (GET "/s/:symbol" [symbol] (stocks/home symbol)))
