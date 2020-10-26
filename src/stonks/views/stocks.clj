(ns stonks.views.stocks
  (:require [hiccup.page :as h]
            [stonks.views.layout :as layout]
            [stonks.client :as client]))

(defn home
  [symbol]
  (layout/page {:title "Search"}
               [:h1 symbol]
               [:div#chart "HIER KOMMT DIE CHART HIN"]
               [:data (client/get-json-monthly symbol)]))
