(ns stonks.routes.stocks
  (:require [compojure.core :refer [defroutes GET]]))

(defroutes routes
  (GET "/s/:symbol" [symbol] {:symbol symbol}))
