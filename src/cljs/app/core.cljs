(ns app.core
  (:require ["apexcharts" :as apex]
            [clojure.string :as str]))

(def ctx (.getElementById js/document "chart"))

(def header {:headers {:Content-Type "application/json"}})

(defn- timestamp->date [val ts]
  (let [d (js/Date. ts)
        year (.getUTCFullYear d)
        month (inc (.getUTCMonth d))]
    (str month "/" year)))

(defn- options [data]
  {:chart {:type "candlestick"}
   :series [{:name "Stonks"
             :data data}]
   :xaxis {:labels {:formatter timestamp->date
                    :style {:fontSize "14px"}}
           :tickAmount 20}
   :yaxis {:labels {:formatter (fn [value] (str value "$"))
                    :style {:fontSize "20px"}}}})

(def stock-symbol (.-textContent (.getElementById js/document "symbol")))

(def url (str (.-origin js/location) "/s/" stock-symbol))

(defn render-chart [] (-> (.fetch js/window url (clj->js header))
                        (.then #(.json %))
                        (.then (fn [data] (.render (apex. ctx (clj->js (options (js->clj data)))))))
                        (.catch #(js/console.error "could not fetch data"))))

(render-chart)
