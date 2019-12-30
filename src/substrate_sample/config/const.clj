(ns substrate-sample.config.const)

(def default-opts
  {:rest-api
   {:name "REST API"
    :port 8080
    :prestop-duration 10}
   :health
   {:name "Health"
    :port 8081
    :prestop-duration 10}})

(def cli-header "Usage: substrate-sample [options]")
(def cli-options
  [["-h" "--help" :id :help?]])
