(ns cljs.app.core
  (:require ["apexcharts" :as apex]
            [clojure.string :as str]
            [goog.functions :as g]
            [cljs.app.chart :as chart]))

(def ctx (.getElementById js/document "chart"))

(def header {:headers {:Content-Type "application/json"}})

(defn- timestamp->date [val ts]
  (let [d (js/Date. ts)
        year (.getUTCFullYear d)
        month (inc (.getUTCMonth d))]
    (str month "/" year)))

(def stock-symbol (.-textContent (.getElementById js/document "symbol")))

(def base-url (str (.-origin js/location) "/s/"))

(defn render-chart [] (-> (.fetch js/window (str base-url stock-symbol) (clj->js header))
                        (.then #(.json %))
                        (.then (fn [data] (.render (apex. ctx (clj->js (chart/options (js->clj data)))))))
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

(defn- divs-from-data [m]
  (->> m
       (map #(js->clj % :keywordize-keys true))
       (mapv (fn [{:keys [symbol name]}]
               (new-div (str symbol " " name))))))

(defn- replace-search-list! [divs]
  (.replaceChildren autocomplete-list)
  (mapv #(append-child autocomplete-list %) divs))

(defn- render-autocomplete! [data]
  (-> data
      divs-from-data
      replace-search-list!)
  (show-element autocomplete-list))

(defn- fetch-symbol-data [symbol]
  (if (str/blank? (.-value input))
    (hide-element autocomplete-list)
    (-> (.fetch js/window (str base-url "search/" symbol) (clj->js header))
        (.then #(.json %))
        (.then #(render-autocomplete! %))
        (.catch #(js/console.error (str
                                    "could not find symbol" " " %))))))

(def debounced-search (g/debounce fetch-symbol-data 200))

(.addEventListener input "keyup", #(debounced-search (str/trim (.-value input))))

(defn main []
  (when ctx
    (render-chart)))
