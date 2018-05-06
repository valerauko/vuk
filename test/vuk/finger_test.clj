(ns vuk.finger-test
  (:require [clojure.test :refer :all]
            [clj-http.fake :refer [with-fake-routes]]
            [vuk.core :refer :all]))

(def user-acct
  "acct:valerauko@pawoo.net")

(def user-url
  "https://pawoo.net/authorize_follow?acct=valerauko@pawoo.net")

(with-fake-routes
  { user-url (fn [request] { :status 200 :headers { :content-type "text/xml" }
                             :body (slurp "fixtures/webfinger.xml")}) }
  (deftest xml-test
    (testing "Can handle XML responses"
      (is (= "acct:valerauko@pawoo.net" (-> user-acct finger :subject))))))

(with-fake-routes
  { user-url (fn [request] { :status 200
                             :headers { :content-type "application/json" }
                             :body (slurp "fixtures/webfinger.json")}) }
  (deftest json-test
    (testing "Can handle JSON responses"
      (is (= "acct:valerauko@pawoo.net" (-> user-acct finger :subject))))))
