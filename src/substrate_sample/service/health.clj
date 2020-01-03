(ns substrate-sample.service.health
  (:require
   [clojure.java.io :as io]
   [com.stuartsierra.component :as component]
   [taoensso.timbre :as timbre]
   [compojure.core :as compojure :refer [defroutes GET]]
   [compojure.route :as route]
   [org.httpkit.server :as server]))

(defn health [req]
  {:status  200
   :headers {"Content-Type" "text/plane"}
   :body    "health"})

(defroutes router
  (GET "/health" [] health)
  (route/not-found (slurp (io/resource "not-found.html"))))

(defrecord HealthComponent [options]
  component/Lifecycle
  (start [this]
    (let [server-name (:name options)]
      (timbre/infof "Starting health: %s..." server-name)
      (let [server (server/run-server router options)
            port (:port options)]
        (timbre/infof "Health %s started with port: %s" server-name port)
        (timbre/infof "Starting health %s completed." server-name)
        (assoc this :health server))))
  (stop [this]
    (let [server-name (:name options)]
      (timbre/infof "Stopping health: %s..." server-name)
      (let [server (:health this)
            timeout (:prestop-duration options)]
        (when server
          (server :timeout timeout)))
      (timbre/infof "Stopping health %s completed." server-name)
      (assoc this :health nil))))

(defn start-health [options]
  (map->HealthComponent
   {:options options}))
