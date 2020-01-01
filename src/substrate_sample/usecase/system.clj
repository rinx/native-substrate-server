(ns substrate-sample.usecase.system
  (:require
   [com.stuartsierra.component :as component]
   [substrate-sample.service.server :as server]
   [substrate-sample.service.health :as health]))

(defn system [{:keys [rest-api health cancel-ch] :as conf}]
  (component/system-map
   :server (server/start-server rest-api)
   :health (component/using
            (health/start-health health)
            {:rest-server :server})))
