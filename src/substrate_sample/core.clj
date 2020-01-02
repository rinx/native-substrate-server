(ns substrate-sample.core
  (:require
   [com.stuartsierra.component :as component]
   [taoensso.timbre :as timbre]
   [substrate-sample.usecase.system :as system])
  (:gen-class))

(set! *warn-on-reflection* true)

(def default-opts
  {:rest-api
   {:name "REST API"
    :port 8080
    :prestop-duration 10}
   :health
   {:name "Health"
    :port 8081
    :prestop-duration 10}})

(def shutdown-hook (atom nil))

(defn run []
  (timbre/set-level! :info)
  (let [opts default-opts
        system (system/system opts)]
    (component/start system)
    (reset! shutdown-hook #(component/stop system))))

(defn -main [& args]
  (-> (Runtime/getRuntime)
      (.addShutdownHook
        (proxy [Thread] []
          (run []
            (timbre/info "System shutdown process started...")
            (let [shutdown-hook (deref shutdown-hook)]
              (when shutdown-hook
                (shutdown-hook)))
            (timbre/info "System shutdown process completed.")))))
  (run))
