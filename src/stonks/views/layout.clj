(ns stonks.views.layout
  (:require [hiccup.page :as h]))

(defn- navbar
  []
  [[:nav.navbar
    [:div.navbar-brand
     [:a.is-size-8.navbar-item {:href "/"}  "📈 Stonks"]]]])

(defn- body
  [content page-type]
  [[:div#content {:class page-type} content]])

(defn page
  [{:keys [title page-type] :as options} & content]
  (h/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]
    [:title (str title " | Stonks")]]
   [:body
    (concat
     (navbar)
     (body content page-type))]
   [:footer
    (h/include-css "/css/bulma.min.css")
    (h/include-js "/js/main.js")
    (h/include-css "/css/styles.css")]))
