(ns stonks.routes.static
  (:require [compojure.core :refer [defroutes GET]]
            [stonks.views.static :as static]))

(defroutes routes
  (GET "/" [] (static/home)))
