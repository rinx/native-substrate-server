(ns dev
  (:require
    [clojure.java.io :as io]
    [clojure.pprint :refer [pprint]]
    [clojure.tools.namespace.repl :as repl
     :refer [refresh refresh-all]]
    [clojure.spec.alpha :as spec]
    [orchestra.spec.test :as stest]
    [com.stuartsierra.component :as component]
    [taoensso.timbre :as timbre]
    [substrate-sample.system :as system]))

(stest/instrument)
(timbre/set-level! :debug)

(def system nil)

(defn init []
  (alter-var-root
    #'system
    (constantly
      (system/system {:port 3000}))))

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
