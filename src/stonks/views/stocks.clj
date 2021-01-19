(ns stonks.views.stocks
  (:require [stonks.views.layout :as layout]
            [stonks.client :as client]
            [clojure.string :as string]
            [ring.util.response :refer [response]]))

(defn home
  [symbol]
  (layout/page {:title "Search"}
               [:div.title.has-text-centered {:id "symbol"} symbol]
               [:div#chart])) 

(defn home-json
  [symbol]
  (response (client/get-monthly-data symbol)))
