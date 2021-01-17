(ns app.core
  (:require ["chart.js" :as Chart]))

(def ctx (.getContext (.getElementById js/document "my-chart") "2d"))

(def data
  {:datasets [{:label "test"
               :data [10 20 30 40 50 60 70]}]
   :labels ["a" "b" "c" "d" "e" "f" "g"]})

(def options
  {:scales {:xAxes [{:ticks {:beginAtZero true}}]}})
(def properties (clj->js {:type "bar" :data data :options options}))


(def my-chart (js/Chart. ctx properties))


;;(println data)
;;(println (clj->js options))
