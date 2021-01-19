(ns app.core
  (:require ["apexcharts" :as apex]
            [clojure.string :as str]))

(def ctx (.getElementById js/document "chart"))

(def header {:headers {:Content-Type "application/json"}})

(defn- options [data]
  {:chart {:type "candlestick"}
   :series [{:name "Stonks"
             :data data}]
   :xaxis {:labels {:format "yyyy"}}})

(def path (.-pathname js/location))


(def stock-symbol (last (clojure.string/split path #"/")))



(defn render-chart [] (-> (.fetch js/window (str "http://localhost:8080/s/" stock-symbol) (clj->js header))
                        (.then #(.json %))
                        (.then (fn [data] (.render (apex. ctx (clj->js (options (js->clj data)))))))
                        (.catch #(js/console.error "could not fetch data"))))

(render-chart)
