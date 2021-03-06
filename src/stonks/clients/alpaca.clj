(ns stonks.clients.alpaca
  (:require [clj-http.client :as client]
            [clojure.string :as string]
            [clojure.data.json :as json]))

(defn key-id [] "PK3K0Y76P09JEB2P3CW9")
(defn secret-key [] "NOntDJxYx3zBbFlNI3JWY9q001DQOoqKOURilHAq")

(def headers {:APCA-API-KEY-ID (key-id)
              :APCA-API-SECRET-KEY (secret-key)})

(def base-url "https://data.alpaca.markets/v2/")

(defn get-json [symbol]
  (let [resp (client/get (str base-url "stocks/" symbol "/bars")
            {:headers headers
             :query-params {:start "2021-01-01T12:00:00Z"
                            :end "2021-03-05T16:01:00Z"
                            :timeframe "1Hour"}
             })]
    (->  (resp :body)
         (json/read-str :key-fn keyword)
         :bars)))
