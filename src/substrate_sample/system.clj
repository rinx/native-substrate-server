(ns substrate-sample.system
  (:require
    [substrate-sample.usecase.health :as health]
    [com.stuartsierra.component :as component]))

(defn system
  [{:keys [port] :as conf}]
  (component/system-map
    :health (component/using (health/start-server port) [])))
