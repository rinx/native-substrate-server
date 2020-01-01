(ns substrate-sample.usecase.system
  (:require
   [substrate-sample.service.server :as server]
   [substrate-sample.service.health :as health]))

(defn system [{:keys [rest-api health cancel-ch] :as conf}]
  {:system (server/start-server rest-api)
   :health (health/start-health health)})
