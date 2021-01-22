(ns cljs.app.core
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
  {:chart {:type "area"
           :width "80%"}
   :fill {:type "gradient"
          :gradient {:shadeIntensity 1
                     :inverseColors false
                     :opacityFrom 0.5
                     :opacityTo 0
                     :stops [0 90 100]}}
   :dataLabels {:enabled false}
   :theme {:palette "palette4"}
   :markers {:size 0}
   
   :series [{:name "Stonks"
             :data data}]
   :xaxis {:type "datetime"
           :labels {:style {:fontSize "15px"
                            :fontFamily "Helvetica, Arial, sans-serif"}}}
   :yaxis {:labels {:formatter (fn [value] (.toFixed value 0))
                    :style {:fontSize "20px"
                            :fontFamily "Helvetica, Arial, sans-serif"}}
           :title {:text "Price in $"
                   :style {:fontSize "20px"
                           :fontFamily "Helvetica, Arial, sans-serif"
                           :fontWeight 400}}}
   :tooltip {:shared false
             :y {:formatter (fn [value] (.toFixed value 0))}}})

(def stock-symbol (.-textContent (.getElementById js/document "symbol")))

(def url (str (.-origin js/location) "/s/" stock-symbol))

(defn render-chart [] (-> (.fetch js/window url (clj->js header))
                        (.then #(.json %))
                        (.then (fn [data] (.render (apex. ctx (clj->js (options (js->clj data)))))))
                        (.catch #(js/console.error "could not fetch data"))))

(defn main []
  (when ctx
    (render-chart)))

(main)
