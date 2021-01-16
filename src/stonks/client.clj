(ns stonks.client
  (:require [clj-http.client :as client]
            [clojure.string :as string]
            [clojure.data.json :as json]))

(def alphavantage-key (System/getenv "ALPHAVANTAGE_KEY"))

(def base-url "https://www.alphavantage.co/query")

(def time-series-monthly "TIME_SERIES_MONTHLY")

(def time-series-intraday "TIME_SERIES_INTRADAY")

(def params {:apikey alphavantage-key})

(def params-monthly (assoc params :function time-series-monthly))

(defn- kebab-case [s]
  (string/lower-case (string/replace s #"\s" "-")))

(defn- get-json
  [func symbol]
  (client/get base-url {:query-params (assoc params :function func :symbol symbol)}))

(defn- get-json-monthly
  [symbol]
  (let [resp (client/get base-url {:query-params (assoc params-monthly :symbol symbol)})]
    (as-> resp r
      (r :body)
      (json/read-str r :key-fn #(keyword (kebab-case %)))
      (r :monthly-time-series))))

(defn- get-json-intraday
  [symbol]
  (let [resp (client/get base-url {:as :json :query-params {:function time-series-intraday
                                                            :apikey alphavantage-key
                                                            :symbol symbol
                                                            :interval "5min"}})]
    (resp :body)))

(defn get-monthly-data [symbol]
  (json/write-str (get-json-monthly symbol)))
