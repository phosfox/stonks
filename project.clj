(defproject stonks "0.1.0-SNAPSHOT"
  :description "stonks"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[lein-ring "0.12.5"]]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.1"]
                 [ring/ring-jetty-adapter "1.8.0"]
                 [ring/ring-core "1.8.0"]
                 [hiccup "1.0.5"]
                 [clj-http "3.10.0"]]
  :main ^:skip-aot stonks.core
  :target-path "target/%s"
  :ring {:handler stonks.core/application}
  :profiles {:uberjar {:aot :all}})
