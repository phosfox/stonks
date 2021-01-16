(ns stonks.views.stocks
  (:require [stonks.views.layout :as layout]
            [stonks.client :as client]
            [clojure.string :as string]
            [clojure.data.json :as json]))

(defn home
  [symbol]
  (layout/page {:title "Search"}
               [:h1 symbol]
               [:div#chart "HIER KOMMT DIE CHART HIN"]
               [:data (client/get-json-monthly (string/upper-case symbol))]))


(defn kebab-case [s]
  (string/lower-case (string/replace s #"\s" "-")))

(def monthly
  (json/write-str (client/get-json-monthly "IBM")
                 :key-fn #(keyword (kebab-case %))))

(keys monthly)
(:monthly-time-series monthly)

(json/read-str "{\"hallo welt\":1,\"b\":2}"
               :key-fn #(keyword (kebab-case %)))

