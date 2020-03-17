(ns stonks.views.stocks
  (:require [hiccup.page :as h]
            [stonks.views.layout :as layout]))

(defn home
  [symbol]
  (layout/page {:title "Search"}
               [:h1 symbol]))
