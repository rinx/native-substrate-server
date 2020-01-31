(defproject substrate-sample "0.1.0-SNAPSHOT"
  :description "A sample project generated using rinx/substrate"
  :url "https://github.com/rinx/substrate-sample"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [com.stuartsierra/component "0.4.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [http-kit "2.3.0"]
                 [compojure "1.6.1"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}
             :uberjar {:aot :all
                       :main substrate-sample.core}}
  :repl-options {:timeout 120000})
