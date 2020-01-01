(ns substrate-sample.service.server
  (:require
   #_[taoensso.timbre :as timbre]
   [compojure.core :as compojure :refer [defroutes GET]]
   [org.httpkit.server :as server]))

(defn root [req]
  {:status  200
   :headers {"Content-Type" "text/plane"}
   :body    "this is root"})

(defroutes router
  (GET "/" [] root))

(defn start-server [options]
  (server/run-server router options))
