(defproject social.kitsune/vuk "0.2.2"
  :description "A Clojure library to handle WebFinger interactions for you."
  :url "http://github.com/valerauko/vuk"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v20.html"}
  :dependencies [[org.clojure/data.xml "0.0.8"]
                 [metosin/jsonista "0.3.8"]
                 [aleph "0.6.3"]]
  :deploy-repositories {"clojars" {:url "https://repo.clojars.org/"
                                   :username :env/clojars_user
                                   :password :env/clojars_token}}
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.1"]]
                   :global-vars {*warn-on-reflection* true
                                 *unchecked-math* :warn-on-boxed}
                   :plugins [[lein-ancient "0.7.0"
                              :exclusions [org.clojure/clojure]]]}
             :clj1.9.0 {:dependencies
                        [[org.clojure/clojure "1.9.0"]]}
             :clj1.10.3 {:dependencies
                         [[org.clojure/clojure "1.10.3"]]}
             :clj1.11.1 {:dependencies
                         [[org.clojure/clojure "1.11.1"]]}})
