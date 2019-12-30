(ns substrate-sample.usecase.health
  (:require [clojure.spec.alpha :as spec]
            [com.stuartsierra.component :as component]
            [org.httpkit.server :as httpkit]
            [clojure.core.async :as async :refer [<! >! <!! >!!]]
            [taoensso.timbre :as timbre]
            [substrate-sample.repository.github :as github]))

(defn handler [req]
  (let [repos (<!! (github/fetch-repos))]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body repos}))

(defrecord ServerComponent [options]
  component/Lifecycle
  (start [this]
    (timbre/info "Starting server...")
    (let [port (get options :port)
          server (httpkit/run-server handler {:port port})]
      (timbre/info "Listening on port:" port)
      (assoc this :server server)))
  (stop [this]
    (timbre/info "Stopping server...")
    ((get this :server) :timeout 1000)
    (assoc this :server nil)))

(defn start-server [port]
  (map->ServerComponent
    {:options {:port port}}))
