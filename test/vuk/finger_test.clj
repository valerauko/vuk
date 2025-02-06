(ns vuk.finger-test
  (:require [aleph.http]
            [clojure.test :refer [deftest testing is]]
            [vuk.core :refer [finger]]))

(def user-acct
  "acct:valerauko@pawoo.net")

(def user-url
  "https://pawoo.net/authorize_follow?acct=valerauko@pawoo.net")

(with-redefs [aleph.http/get (fn [_uri]
                               {:status 200
                                :headers {:content-type "application/xml"}
                                :body (slurp "fixtures/webfinger.xml")})]
  (deftest xml-test
    (testing "Can handle XML responses"
      (is (= user-acct (-> user-acct finger :subject))))))

(with-redefs [aleph.http/get (fn [_uri]
                               {:status 200
                                :headers {:content-type "application/json"}
                                :body (slurp "fixtures/webfinger.json")})]
  (deftest json-test
    (testing "Can handle JSON responses"
      (is (= user-acct (-> user-acct finger :subject))))))
