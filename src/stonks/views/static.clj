(ns stonks.views.static
  (:require [stonks.views.layout :as layout]
            [hiccup.form :refer [form-to]]))

(defn home
  []
  (layout/page {:title "Home" :page-type "home"}
               [:section.hero.is-primary.is-medium.start-section-padding
                [:div.hero-body
                 [:div.container.has-text-centered
                  [:h1.title "Search for a stock symbol"]
                  [:h2.subtitle "Get charts of your favourite stocks"]]
                 [:div.control
                  (form-to {:autocomplete "off"}
                           [:get "/s"]
                           [:div {:class "autocomplete"}
                            [:input.input
                             {:type "text"
                              :placeholder "AAPL, GOOG"
                              :name "symbol"}
                             [:div {:class "autocomplete-items" :id "autocomplete-list"}]]])]]]))

(defn not-found
  []
  (layout/page {:title "This page does not exist" :page-type "404"}
               [:section.section.has-text-centered
                [:div.container
                 [:p.is-size-3 "This page does not exist"]]]))
