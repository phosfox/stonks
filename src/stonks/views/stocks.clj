(ns stonks.views.stocks
  (:require [stonks.views.layout :as layout]
            [stonks.client :as client]
            [ring.util.response :refer [response]]
            [hiccup.form :refer [form-to]]))

(defn home
  [symbol]
  (layout/page {:title "Search"}
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
               [:div.title.has-text-centered {:id "symbol"} symbol]
               [:div {:class "container" :id "chart"}])) 

(comment)

(defn home-json
  [symbol]
  (response (client/get-monthly-data symbol)))

(defn search-symbol
  [keywords]
  (response (client/search-symbol keywords)))
