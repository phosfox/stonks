(ns stonks.routes.stocks
  (:require [compojure.core :refer [defroutes GET]]
            [stonks.views.stocks :as stocks]))

(defn- json-request? [request]
  (if-let [type (get-in request [:headers "content-type"])]
    (not (empty? (re-find #"^application/(.+\+)?json" type)))))

(defroutes routes
  (GET "/s" {params :query-params} (stocks/home (get params "symbol")))
  (GET "/s/:symbol" [symbol :as req] (if (json-request? req)
                                       (stocks/home-json symbol)
                                       (stocks/home symbol)))
  (GET "/s/search/:symbol" [symbol :as req] (stocks/search-symbol symbol)))

