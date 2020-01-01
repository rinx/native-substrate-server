(ns substrate-sample.service.health
  (:require
   [com.stuartsierra.component :as component]
   #_[taoensso.timbre :as timbre]
   [compojure.core :as compojure :refer [defroutes GET]]
   [org.httpkit.server :as server]))

(defn health [req]
  {:status  200
   :headers {"Content-Type" "text/plane"}
   :body    "health check completed"})

(defroutes router
  (GET "/health" [] health))

(defrecord HealthComponent [options]
  component/Lifecycle
  (start [this]
    (let [server-name (:name options)]
      #_(timbre/infof "Starting health server: %s..." server-name)
      (let [server (server/run-server router options)
            port (:port options)]
        #_(timbre/infof "Health server %s started with port :%s" server-name port)
        #_(timbre/infof "Starting health %s completed." server-name)
        (assoc this :server server))))
  (stop [this]
    (let [server-name (:name options)]
      #_(timbre/infof "Stopping health server: %s..." server-name)
      (let [server (:server this)
            timeout (:prestop-duration options)]
        (when server
          (server :timeout timeout)))
      #_(timbre/infof "Stopping health server %s completed." server-name)
      (assoc this :server nil))))

(defn start-health [options]
  (map->HealthComponent
    {:options options}))
