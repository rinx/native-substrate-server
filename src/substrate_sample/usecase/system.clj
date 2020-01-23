(ns substrate-sample.usecase.system
  (:require [com.stuartsierra.component :as component]
            [clj-halodb.core :as halodb]
            [substrate-sample.service.server :as server]))

(defn system [{:keys [rest-api liveness readiness] :as conf}]
  (component/system-map
    :db (halodb/open
          "/etc/server/halodb"
          (halodb/options
            {:max-file-size 131072
             :sync-write true}))
    :liveness (server/start-server liveness)
    :rest (component/using
            (server/start-server rest-api)
            {:liveness :liveness})
    :readiness (component/using
                 (server/start-server readiness)
                 {:rest :rest})))
