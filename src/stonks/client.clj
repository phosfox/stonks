(ns stonks.client
  (:require [clj-http.client :as client]
            [clojure.string :as string]
            [clojure.data.json :as json]))

(def alphavantage-key (System/getenv "ALPHAVANTAGE_KEY"))

#_(assert (not= (alphavantage-key) nil))

(def base-url "https://www.alphavantage.co/query")

(def time-series-monthly "TIME_SERIES_MONTHLY")

(def time-series-intraday "TIME_SERIES_INTRADAY")

(def symbol-search "SYMBOL_SEARCH")

(def params {:apikey alphavantage-key})

(def params-monthly (assoc params :function time-series-monthly))

(defn- kebab-case [s]
  (string/lower-case (string/replace s #"\s" "-")))

(defn take-last-split [s]
  (string/lower-case (last (string/split s #"\s"))))

(defn- get-json
  [func symbol]
  (client/get base-url {:query-params (assoc params :function func :symbol symbol)}))

(defn- get-json-search [keywords]
  (let [resp (client/get base-url {:query-params (assoc params :function symbol-search :keywords keywords)})
        matches (-> resp
                    (:body)
                    (json/read-str , :key-fn #(keyword (take-last-split %)))
                    :bestmatches)]
    (->> matches
         (mapv (fn [{:keys [symbol name matchscore]}] {:name name :symbol symbol :matchscore matchscore})))))

(defn search-symbol [keywords]
  (get-json-search keywords))

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

(defn date->timestamp [date]
  (.getEpochSecond (.toInstant (java.sql.Timestamp/valueOf (str date " 19:00:00")))))

(defn- to-apex-candlestick [[date values]]
  (let [open (:1.-open values)
        high (:2.-high values)
        low (:3.-low values)
        close (:4.-close values)
        data (mapv parse-double [open high low close])]
    [(* 1000 (date->timestamp (name date))) data]))

(defn- to-apex-line [[date values]]
  (let [close (:4.-close values)
        ts (* 1000 (date->timestamp (name date)))]
    {:x ts :y (parse-double close)}))


(defn get-monthly-data [symbol]
  (->> (get-json-monthly symbol)
       (map to-apex-line)
       (sort-by :x)
       json/write-str))

  
