(ns stonks.client
  (:require [clj-http.client :as client]))

(defn get-symbol
  [symbol]
  (client/get "https://jsonplaceholder.typicode.com/posts/1"))
(get-symbol "bla")

(System/getenv "ALPHAVANTAGE_KEY")
