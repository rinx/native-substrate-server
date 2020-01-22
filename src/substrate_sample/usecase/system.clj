(ns substrate-sample.usecase.system
  (:require [com.stuartsierra.component :as component]
            [substrate-sample.service.health :as health]
            [substrate-sample.service.server :as server]))

(defn system [{:keys [rest-api liveness readiness] :as conf}]
  (component/system-map
   :liveness (health/start-health liveness)
   :server (component/using
            (server/start-server rest-api)
            {:liveness :liveness})
   :readiness (component/using
               (health/start-health readiness)
               {:server :server})))
