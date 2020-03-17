(ns stonks.client
  (:require [clj-http.client :as client]))

(def alphavantage-key (System/getenv "ALPHAVANTAGE_KEY"))

(def base-url "https://www.alphavantage.co/query")

(def time-series-monthly "TIME_SERIES_MONTHLY")

(def time-series-intraday "TIME_SERIES_INTRADAY")

(def params {:datatype "json" :apikey alphavantage-key})

(def params-monthly (assoc params :function time-series-monthly))

(defn get-json
  [func s]
  (client/get base-url {:query-params (assoc params :function func :symbol s)}))

(defn get-json-monthly
  [s]
  (let [resp (client/get base-url {:query-params (assoc params-monthly :symbol s)})]
    (resp :body)))

(defn get-json-intraday
  [s]
  (let [resp (client/get base-url {:as :json :query-params {:function time-series-intraday
                                                            :apikey alphavantage-key
                                                            :symbol s
                                                            :interval "5min"}})]
    (resp :body)))
