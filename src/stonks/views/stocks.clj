(ns stonks.views.stocks
  (:require [stonks.views.layout :as layout]
            [stonks.client :as client]
            [clojure.string :as string]
            [clojure.data.json :as json]))

(defn home
  [symbol]
  (layout/page {:title "Search"}
               [:h1 symbol]
               [:canvas#my-chart {:height 385 :width 720}]))
