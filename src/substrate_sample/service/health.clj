(ns substrate-sample.service.health
  (:require
   #_[taoensso.timbre :as timbre]
   [compojure.core :as compojure :refer [defroutes GET]]
   [org.httpkit.server :as server]))

(defn health [req]
  {:status  200
   :headers {"Content-Type" "text/plane"}
   :body    "health check completed"})

(defroutes router
  (GET "/health" [] health))

(defn start-health [options]
  (server/run-server router options))
