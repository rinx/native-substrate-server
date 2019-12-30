(ns substrate-sample.core
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.core.async :as async :refer [<! >! <!! >!!]]
   [clojure.tools.cli :as cli]
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [com.stuartsierra.component :as component]
   [substrate-sample.config.const :as config]
   [substrate-sample.usecase.system :as system]
   [taoensso.timbre :as timbre])
  (:gen-class))

(defn parse-int [s]
  (try
    (Integer/parseInt s)
    (catch Exception _ nil)))

(defn run [ctx config]
  (timbre/set-level! :info)
  (let [cancel-ch (:cancel-ch ctx)
        opts (-> config
                 (->> (merge-with merge config/default-opts))
                 (into {:cancel-ch cancel-ch}))
        system (system/system opts)]
    (component/start system)
    (async/go
      (let [wait-ch (<! cancel-ch)]
        (timbre/info "System shutdown process started...")
        (component/stop system)
        (>! wait-ch :ok)
        (timbre/info "System shutdown process completed.")
        (System/exit 0)))))

(defn main
  [ctx {:keys [options summary] :as parsed-result}]
  (let [{:keys [config-filename help?]} options]
    (if help?
      (do
        (println config/cli-header)
        (println summary))
      (let [config (try
                     (-> config-filename
                         (io/resource)
                         (slurp)
                         (edn/read-string))
                     (catch Exception _
                       (println "not found:" config-filename)
                       {}))]
        (run ctx config)))))

(defn -main [& args]
  (let [cancel-ch (async/chan)
        ctx {:cancel-ch cancel-ch}]
    (-> (Runtime/getRuntime)
        (.addShutdownHook
          (proxy [Thread] []
            (run []
              (let [wait-ch (async/chan)]
                (async/put! cancel-ch wait-ch)
                (<!! wait-ch))))))
    (-> args
        (cli/parse-opts config/cli-options)
        (->> (main ctx)))))
