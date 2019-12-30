(ns substrate-sample.service.router
  (:require
   [com.stuartsierra.component :as component]
   [taoensso.timbre :as timbre]))

(defrecord RouterComponent [options]
  component/Lifecycle
  (start [this]
    (timbre/info "Starting router...")
    (let [handlers (get-in this [:handler :handler])
          router (:root handlers)]
      (timbre/info "Starting router completed.")
      (assoc this :router router)))
  (stop [this]
    (timbre/info "Stopping router...")
    (timbre/info "Stopping router completed.")
    (assoc this :router nil)))

(defn start-router [options]
  (map->RouterComponent
   {:options options}))
