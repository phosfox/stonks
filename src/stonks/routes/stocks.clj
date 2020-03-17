(ns stonks.routes.stocks
  (:require [compojure.core :refer [defroutes GET]]
            [stonks.views.stocks :as stocks]))

(defroutes routes
  (GET "/s/:symbol" [symbol & rest] (stocks/home symbol)))
