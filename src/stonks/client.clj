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

(defn- closing-value "keeps just the closing value" [monthly-data]
  (map (fn [[k v]] {k (:4.-close v)}) monthly-data))

(defn- parse-double [s]
  (Double/parseDouble s))

(defn get-monthly-data [symbol]
  (->> (get-json-monthly symbol)
       (map to-apex-format)
       sort
       json/write-str))

(defn date->timestamp [date]
  (.getEpochSecond (.toInstant (java.sql.Timestamp/valueOf (str date " 19:00:00")))))

(defn to-apex-format [[date values]]
  (let [open (:1.-open values)
        high (:2.-high values)
        low (:3.-low values)
        close (:4.-close values)
        data (mapv parse-double [open high low close])]
    [(* 1000 (date->timestamp (name date))) data]))

(count (str (* 1000 1567697090)))
