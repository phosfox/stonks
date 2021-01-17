(ns stonks.views.stocks
  (:require [stonks.views.layout :as layout]
            [stonks.client :as client]
            [clojure.string :as string]
            [ring.util.response :refer [response]]))

(defn home
  [symbol]
  (layout/page {:title "Search"}
               [:h1 symbol]
               [:canvas#my-chart {:height 385 :width 720}]))

(defn home-json
  [symbol]
  (response (client/get-monthly-data symbol)))
