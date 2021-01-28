(ns cljs.app.chart)

(defn options [data]
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
