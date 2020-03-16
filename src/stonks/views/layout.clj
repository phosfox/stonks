(ns stonks.views.layout
  (:require [hiccup.page :as h]))

(defn- navbar
  []
  [[:nav.navbar
    [:div.navbar-brand
     [:a.is-size-4.navbar-item {:href "/"}  "📈 Stonks"]]]])

(defn- body
  [content]
  [[:div#content content]])

(defn page
  [{:keys [title] :as options} & content]
  (h/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]
    (h/include-css "css/bulma.min.css")
    [:title (str title " | Stonks")]]
   [:body
    (concat
     (navbar)
     (body content))]))
