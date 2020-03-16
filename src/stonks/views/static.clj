(ns stonks.views.static
  (:require [stonks.views.layout :as layout]
            [hiccup.form :refer [form-to]]))

(defn home
  []
  (layout/page {:title "Home"}
               [:section.hero.is-primary.is-medium.start-section-padding
                [:div.hero-body
                 [:div.container.has-text-centered
                  [:h1.title "Search for a stock symbol"]
                  [:h2.subtitle "Get charts of your favourite stocks"]]
                 [:div.field
                  [:div.control
                   (form-to [:get "/s/"]
                    [:input.input.is-large.is-primary {:type "text" :placeholder "APPL, GOOG..." :name "symbol" :id "symbol"}])]]]]))

(defn not-found
  []
  (layout/page {:title "This page does not exist"}
               [:section.section.has-text-centered
                [:div.container
                 [:p.is-size-3 "This page does not exist"]]]))
