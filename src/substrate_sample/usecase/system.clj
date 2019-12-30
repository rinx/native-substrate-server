(ns substrate-sample.usecase.system
  (:require
   [com.stuartsierra.component :as component]
   [substrate-sample.service.server :as server]
   [substrate-sample.service.router :as router]
   [substrate-sample.service.health :as health]
   [substrate-sample.handler.rest :as handler-rest]))

(defn system [{:keys [rest-api health cancel-ch] :as conf}]
  (component/system-map
   :handler-rest (handler-rest/start-handler {})
   :router (component/using
            (router/start-router {})
            {:handler :handler-rest})
   :health-router (health/start-health)
   :server (component/using
            (server/start-server rest-api)
            {:router :router})
   :health (component/using
            (server/start-server health)
            {:router :health-router
             :rest-server :server})))
