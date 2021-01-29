(ns stonks.views.stocks
  (:require [stonks.views.layout :as layout]
            [stonks.client :as client]
            [ring.util.response :refer [response]]
            [hiccup.form :refer [form-to]]
            [clojure.string :as str]))

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
               [:div {:class "container" :id "chart"}])) 

(defn home-json
  [symbol]
  (response (client/get-monthly-data symbol)))

(defn search-symbol
  [keywords]
  (response (client/search-symbol keywords)))
