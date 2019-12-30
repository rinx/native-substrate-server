(ns substrate-sample.handler.rest
  (:require
   [com.stuartsierra.component :as component]
   [taoensso.timbre :as timbre]))

(defn root [req]
  {:status  200
   :headers {"Content-Type" "text/plane"}
   :body    "root handler"})

(defrecord HandlerComponent [options]
  component/Lifecycle
  (start [this]
    (timbre/info "Starting handler...")
    (let [handler {:root root}]
      (timbre/info "Starting handler completed.")
      (assoc this :handler handler)))
  (stop [this]
    (timbre/info "Stopping handler...")
    (timbre/info "Stopping handler completed.")
    (assoc this :handler nil)))

(defn start-handler [options]
  (map->HandlerComponent
   {:options options}))
