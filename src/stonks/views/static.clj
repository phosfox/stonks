(ns stonks.views.static
  (:require [stonks.views.layout :as layout]))

(defn home
  []
  (layout/page {:title "Home"}))

(defn not-found
  []
  (layout/page {:title "This page does not exist"}))
