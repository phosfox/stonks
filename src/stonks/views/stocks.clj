(ns stonks.views.stocks
  (:require [stonks.views.layout :as layout]
            [stonks.clients.alphavantage :as alpha]
            [ring.util.response :refer [response content-type]]
            [hiccup.form :refer [form-to]]
            [clojure.string :as str]
            [clojure.data.json :as json]))

(defn home
  [symbol]
  (layout/page {:title "Search" :page-type "stocks"}
               [:level
                [:level-item.field
                 [:div.control
                  (form-to {:autocomplete "off"}
                           [:get "/s"]
                           [:div {:class "autocomplete"}
                            [:input.input
                              {:type "text"
                               :placeholder "AAPL, GOOG"
                               :name "symbol"}
                             [:div {:class "autocomplete-items" :id "autocomplete-list"}]]])]]]
               [:div.title.has-text-centered {:id "symbol"} (str/upper-case symbol)]
               [:div {:class "container" :id "chart"}]
               [:div {:data-chart (alpha/get-monthly-data symbol)}]))

(defn home-json
  [symbol]
  (content-type (response (alpha/get-monthly-data symbol)) "application/json"))

(defn search-symbol
  [keywords]
  (content-type (response (alpha/search-symbol keywords)) "application/json"))
