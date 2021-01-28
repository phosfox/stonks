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

(def autocomplete-list (.getElementById js/document "autocomplete-list"))

(def input (.querySelector js/document "input"))

(defn show-element [ele]
  (set! (.-visibility (.-style ele)) "visible"))

(defn hide-element [ele]
  (set! (.-visibility (.-style ele)) "hidden"))

(defn render-chart []
   (-> (.fetch js/window (str base-url stock-symbol) (clj->js header))
       (.then #(.json %))
       (.then (fn [data] (.render (apex. ctx (clj->js (chart/options (js->clj data)))))))
       (.catch #(js/console.error "could not fetch data"))))





(defn- new-list-item [symbol name]
  (let [div (.createElement js/document "div")]
    (set! (.-textContent div) (str symbol " " name))
    (set! (.-id div) symbol)
    (set! (.-className div) "autocomplete-item")
    div))

(defn- append-child [parent child]
  (.appendChild parent child))

  
(def not-blank? (complement str/blank?))

(defn- divs-from-data [m]
  (->> m
       (map #(js->clj % :keywordize-keys true))
       (mapv (fn [{:keys [symbol name]}]
               (new-list-item symbol name)))))

(defn- replace-search-list! [divs]
  (.replaceChildren autocomplete-list)
  (mapv #(append-child autocomplete-list %) divs))

(defn- replace-search-with [string]
  (set! (.-value input) string))

(defn- render-autocomplete! [data]
  (-> data
      divs-from-data
      replace-search-list!)
  (show-element autocomplete-list)
  (mapv
   (fn [item]
     (.addEventListener item "click" #(set! (.-href js/location) (str base-url (.-id (.-srcElement %)))))) ;This is not very clean
   (.querySelectorAll js/document ".autocomplete-item")))

(defn- fetch-symbol-data [symbol]
  (if (str/blank? (.-value input))
    (hide-element autocomplete-list)
    (-> (.fetch js/window (str base-url "search/" symbol) (clj->js header))
        (.then #(.json %))
        (.then #(render-autocomplete! %))
        (.catch #(js/console.error (str
                                    "could not find symbol" " " %))))))

(def debounced-search (g/debounce fetch-symbol-data 200))

(.addEventListener input "keyup" #(debounced-search (str/trim (.-value input))))


(defn main []
  (when ctx
    (render-chart)))
