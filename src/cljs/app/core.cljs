(ns cljs.app.core
  (:require ["apexcharts" :as apex]
            [clojure.string :as str]
            [goog.functions :as g]))

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

(def base-url (str (.-origin js/location) "/s/"))

(defn render-chart [] (-> (.fetch js/window (str base-url stock-symbol) (clj->js header))
                        (.then #(.json %))
                        (.then (fn [data] (.render (apex. ctx (clj->js (options (js->clj data)))))))
                        (.catch #(js/console.error "could not fetch data"))))

(def input (.querySelector js/document "input"))

(def autocomplete-list (.getElementById js/document "autocomplete-list"))

(defn- new-div [text]
  (let [div (.createElement js/document "div")]
    (set! (.-textContent div) text)
    div))


(defn- append-child [parent child]
  (.appendChild parent child))

(defn show-element [ele]
  (set! (.-visibility (.-style ele)) "visible"))

(defn hide-element [ele]
  (set! (.-visibility (.-style ele)) "hidden"))
  

(def not-blank? (complement str/blank?))

(defn- search-symbol [symbol]
  (if (str/blank? (.-value input))
    (hide-element autocomplete-list)
    (-> (.fetch js/window (str base-url "search/" symbol) (clj->js header))
        (.then #(.json %))
        (.then #(mapv (fn [{:keys [name symbol]}] (new-div (str symbol " " name))) (js->clj % :keywordize-keys true)))
        (.then (fn [divs] (do (.replaceChildren autocomplete-list)
                              (mapv #(append-child autocomplete-list %) divs))))
        (.then (show-element autocomplete-list))
        (.catch #(js/console.error "could not find symbol")))))

(def debounced-search (g/debounce search-symbol 200))

(.addEventListener input "keyup", #(debounced-search (str/trim (.-value input))))

(defn main []
  (when ctx
    (render-chart)))
