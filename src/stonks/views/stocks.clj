(ns stonks.views.stocks
  (:require [hiccup.page :as h]
            [stonks.views.layout :as layout]))

(defn home
  []
  (layout/page {:title "Search"}))
