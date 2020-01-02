(ns substrate-sample.service.server
  (:require
   [clojure.java.io :as io]
   [com.stuartsierra.component :as component]
   [taoensso.timbre :as timbre]
   [compojure.core :as compojure :refer [defroutes GET]]
   [compojure.route :as route]
   [org.httpkit.server :as server]))

(defn root [req]
  {:status  200
   :headers {"Content-Type" "text/plane"}
   :body    "this is root"})

(defroutes router
  (GET "/" [] root)
  (route/not-found (slurp (io/resource "not-found.html"))))

(defrecord ServerComponent [options]
  component/Lifecycle
  (start [this]
    (let [server-name (:name options)]
      (timbre/infof "Starting server: %s..." server-name)
      (let [server (server/run-server router options)
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
