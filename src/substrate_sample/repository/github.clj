(ns substrate-sample.repository.github
  (:require [clj-http.lite.client :as http]
            [taoensso.timbre :as timbre]
            [clojure.core.async :as async :refer [<! >! <!! >!!]]
            [jsonista.core :as jsonista]
            [camel-snake-kebab.core :as csk]))

(def json-mapper
  (jsonista/object-mapper
   {:pretty true
    :decode-key-fn csk/->kebab-case-keyword}))

(def url "https://api.github.com/users/rinx/repos")

(defn fetch-repos []
  (let [ch (async/chan)]
    (async/go
      (-> url
          (http/get)
          :body
          (jsonista/read-value json-mapper)
          (->> (async/put! ch))))
    ch))
