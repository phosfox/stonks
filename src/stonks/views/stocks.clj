(ns stonks.views.stocks
  (:require [stonks.views.layout :as layout]
            [stonks.client :as client]
            [clojure.string :as string]
            [clojure.data.json :as json]))

(defn home
  [symbol]
  (layout/page {:title "Search"}
               [:h1  symbol]
               [:canvas#chart2 {:height 400 :width 400} "HIER KOMMT DIE CHART HIN"]
               [:data {:style "visibility:hidden"} (client/get-monthly-data (string/upper-case symbol))]))
