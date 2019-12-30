(ns substrate-sample.service.health
  (:require
   [com.stuartsierra.component :as component]
   [taoensso.timbre :as timbre]
   [compojure.core :as compojure :refer [defroutes GET]]))

(defn health [req]
  {:status  200
   :headers {"Content-Type" "text/plane"}
   :body    "health check completed"})

(defroutes router
  (GET "/health" [] health))

(defrecord HealthComponent []
  component/Lifecycle
  (start [this]
    (timbre/info "Starting health")
    (timbre/info "Starting health completed.")
    (assoc this :router router))
  (stop [this]
    (timbre/info "Stopping health...")
    (timbre/info "Stopping health completed.")
    (assoc this :router nil)))

(defn start-health []
  (map->HealthComponent {}))
