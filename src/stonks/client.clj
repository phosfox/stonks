(ns stonks.client
  (:require [clj-http.client :as client]))

(def alphavantage-key (System/getenv "ALPHAVANTAGE_KEY"))

(def base-url "https://www.alphavantage.co/query")

(def time-series-monthly "TIME_SERIES_MONTHLY")

(defn get-json
  [func s]
  (client/get base-url {:query-params {:function func :datatype "json" :symbol s :apikey alphavantage-key}}))

(defn get-monthly
  [s]
  (partial get-json time-series-monthly))
