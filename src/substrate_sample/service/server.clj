(ns substrate-sample.service.server
  (:require
   [clojure.spec.alpha :as spec]
   [com.stuartsierra.component :as component]
   [taoensso.timbre :as timbre]
   [org.httpkit.server :as server]))

(defrecord ServerComponent [options]
  component/Lifecycle
  (start [this]
    (let [server-name (:name options)]
      (timbre/infof "Starting server: %s..." server-name)
      (let [router (get-in this [:router :router])
            server (server/run-server router options)
            port (:port options)]
        (timbre/infof "Server %s started with port: %s" server-name port)
        (timbre/infof "Starting server %s completed." server-name)
        (assoc this :server server))))
  (stop [this]
    (let [server-name (:name options)]
      (timbre/infof "Stopping server: %s..." server-name)
      (let [server (:server this)
            timeout (:prestop-duration options)]
        (when server
          (server :timeout timeout)))
      (timbre/infof "Stopping server %s completed." server-name)
      (assoc this :server nil))))

(defn start-server [options]
  (map->ServerComponent
   {:options options}))
