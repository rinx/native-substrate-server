(defproject substrate-sample "0.1.0-SNAPSHOT"
  :description "FIXME"
  :url "https://github.com/rinx/substrate-sample"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 #_[org.clojure/spec.alpha "0.2.176"]
                 [org.clojure/core.async "0.4.490"]
                 [com.stuartsierra/component "0.4.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [http-kit "2.3.0"]
                 [org.martinklepsch/clj-http-lite "0.4.3"]
                 [metosin/jsonista "0.2.2"]
                 [camel-snake-kebab "0.4.0"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [orchestra "2019.02.06-1"]]
                   :source-paths ["dev"]}
             :uberjar {:aot :all
                       :main substrate-sample.core}})
