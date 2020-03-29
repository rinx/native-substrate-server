(ns substrate-sample.usecase.system
  (:require [com.stuartsierra.component :as component]
            [substrate-sample.service.server :as server]
            [substrate-sample.service.halodb :as halodb]))

(defn system [{:keys [rest-api liveness readiness] :as conf}]
  (component/system-map
    :liveness (server/start-server liveness)
    :db (halodb/start-halodb {})
    :rest (component/using
            (server/start-server rest-api)
            {:liveness :liveness
             :halodb :db})
    :readiness (component/using
                 (server/start-server readiness)
                 {:rest :rest})))
