(defproject substrate-sample "0.1.0-SNAPSHOT"
  :description "A native server template"
  :url "https://github.com/rinx/native-substrate-server"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.2-alpha1"]
                 [org.clojure/spec.alpha "0.2.187"]
                 [org.clojure/core.async "0.7.559"]
                 [com.stuartsierra/component "0.4.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [http-kit "2.3.0"]
                 [compojure "1.6.1"]
                 [clj-halodb "0.0.3"]]
  :plugins [[io.taylorwood/lein-native-image "0.3.1"]]
  :native-image {:name "server"
                 :opts ["-H:+ReportExceptionStackTraces"
                        "-H:Log=registerResource:"
                        "-H:ConfigurationFileDirectories=native-config"
                        "--enable-url-protocols=http,https"
                        "--enable-all-security-services"
                        "--no-fallback"
                        "--no-server"
                        "--verbose"
                        "--static"
                        "--report-unsupported-elements-at-runtime"
                        "--initialize-at-build-time"
                        "--allow-incomplete-classpath"
                        "-J-Xms2g"
                        "-J-Xmx7g"]
                 :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}
             :uberjar {:aot :all
                       :main substrate-sample.core}}
  :repl-options {:timeout 120000})
