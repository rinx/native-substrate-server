(defproject substrate-sample "0.1.0-SNAPSHOT"
  :description "A sample project generated using rinx/substrate"
  :url "https://github.com/rinx/substrate-sample"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 #_[org.clojure/spec.alpha "0.2.176"]
                 [org.clojure/core.async "0.4.500"]
                 [org.clojure/tools.cli "0.4.2"]
                 [com.stuartsierra/component "0.4.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [http-kit "2.3.0"]
                 [compojure "1.6.1"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [orchestra "2019.02.06-1"]]
                   :source-paths ["dev"]}
             :uberjar {:aot :all
                       :main substrate-sample.core}})
