(ns substrate-sample.core
  (:require
   #_[clojure.core.async :as async :refer [<! >! <!! >!!]]
   [substrate-sample.usecase.system :as system]
   #_[taoensso.timbre :as timbre])
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

(defn run [ctx]
  #_(timbre/set-level! :info)
  (let [cancel-ch (:cancel-ch ctx)
        opts (-> default-opts
                 (into {:cancel-ch cancel-ch}))
        system (system/system opts)]
    #_(async/go
      (let [wait-ch (<! cancel-ch)]
        #_(timbre/info "System shutdown process started...")
        ((:system system))
        ((:health system))
        (>! wait-ch :ok)
        #_(timbre/info "System shutdown process completed.")
        (System/exit 0)))))

(defn -main [& args]
  (let [#_#_cancel-ch (async/chan)
        ctx {:cancel-ch nil #_cancel-ch}]
    #_(-> (Runtime/getRuntime)
        (.addShutdownHook
          (proxy [Thread] []
            (run []
              (let [wait-ch (async/chan)]
                (async/put! cancel-ch wait-ch)
                (<!! wait-ch))))))
    (run ctx)))
