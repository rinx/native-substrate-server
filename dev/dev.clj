(ns dev
  (:require
   [clojure.java.io :as io]
   [clojure.pprint :refer [pprint]]
   [clojure.tools.namespace.repl :as repl
    :refer [refresh refresh-all]]
   [clojure.spec.alpha :as spec]
   [clojure.core.async :as async]
   [orchestra.spec.test :as stest]
   [com.stuartsierra.component :as component]
   [taoensso.timbre :as timbre]
   [substrate-sample.config.const :as config]
   [substrate-sample.usecase.system :as system]))

(stest/instrument)
(timbre/set-level! :debug)

(def system nil)

(defn init []
  (alter-var-root
    #'system
    (constantly
      (system/system
        (into config/default-opts
              {:cancel-ch (async/chan)})))))

(defn start []
  (alter-var-root
   #'system
   component/start))

(defn stop []
  (alter-var-root #'system
                  (fn [s]
                    (when s (component/stop s)))))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (refresh :after 'dev/go))
