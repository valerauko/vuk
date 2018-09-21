(defproject social.kitsune/vuk "0.1.0"
  :description "A Clojure library to handle WebFinger interactions for you."
  :url "http://github.com/valerauko/vuk"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v20.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [metosin/jsonista "0.2.1"]
                 [clj-http "3.9.0"]]
  :profiles {:dev {:dependencies [[clj-http-fake "1.0.3"]]}})
