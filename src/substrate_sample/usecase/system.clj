(ns substrate-sample.usecase.system
  (:require [com.stuartsierra.component :as component]
            [substrate-sample.service.server :as server]))

(defn system [{:keys [rest-api liveness readiness] :as conf}]
  (component/system-map
    :liveness (server/start-server liveness)
    :rest (component/using
            (server/start-server rest-api)
            {:liveness :liveness})
    :readiness (component/using
                 (server/start-server readiness)
                 {:rest :rest})))
