(defproject social.kitsune/vuk "0.2.2"
  :description "A Clojure library to handle WebFinger interactions for you."
  :url "http://github.com/valerauko/vuk"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v20.html"}
  :scm {:name "git"
        :url "https://github.com/valerauko/vuk"
        :tag "v0.2.2"}
  :dependencies [[org.clojure/data.xml "0.0.8"]
                 [metosin/jsonista "0.3.13"]
                 [aleph "0.8.2"]]
  :deploy-repositories {"clojars" {:url "https://repo.clojars.org/"
                                   :username :env/clojars_user
                                   :password :env/clojars_token}}
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.12.0"]]
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
