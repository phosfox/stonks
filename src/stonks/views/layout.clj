(ns stonks.views.layout
  (:require [hiccup.page :as h]))

(defn page
  [{:keys [title] :as options} & content]
  (h/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]
    [:link {:rel "icon" :href "/favicon.ico" :type "image/x-icon"}]
    [:title (str title " | Stonks")]]
   [:body
    [:h1 "This is the body"]]))
