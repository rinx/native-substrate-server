(ns substrate-sample.service.router
  (:require
   [com.stuartsierra.component :as component]
   [taoensso.timbre :as timbre]
   [compojure.core :as compojure :refer [defroutes GET POST PUT DELETE PATCH context]]))

(declare router)

(defn define-route
  [{:keys [root]}]
  (defroutes router
    (GET "/" [] root)))

(defrecord RouterComponent [options]
  component/Lifecycle
  (start [this]
    (timbre/info "Starting router...")
    (let [handlers (get-in this [:handler :handler])]
      (define-route handlers))
    (timbre/info "Starting router completed.")
    (assoc this :router router))
  (stop [this]
    (timbre/info "Stopping router...")
    (timbre/info "Stopping router completed.")
    (assoc this :router nil)))

(defn start-router [options]
  (map->RouterComponent
   {:options options}))
