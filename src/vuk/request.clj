(ns vuk.request
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [clojure.xml :as xml]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn standard-url
  "The common format of WebFinger URLs"
  [{:keys [scheme domain user] :or {scheme "https"}}]
  (str scheme "://" domain "/.well-known/webfinger?resource=" user))

(defn host-meta
  "The WebFinger host metadata URL"
  [{:keys [scheme domain] :or {scheme "https"}}]
  (str scheme "://" domain "/.well-known/host-meta"))

(defn acct-data
  "Returns an user ID - host list"
  [acct]
  (->> acct (re-find #"(?i)acct:(\w+@(.+))") rest))

(defn lookup-standard
  [acct options]
  (let [[user host] (acct-data acct)
        uri (standard-url (merge {:domain host :user user} options))]
    (http/get uri)))

(defn lookup-template
  "xml/parse will raise various errors if there is no template or it's not xml"
  [acct options]
  (let [[user host] (acct-data acct)
        meta-url (host-meta (merge {:domain host} options))]
    (as-> meta-url v
      (xml/parse v) ; host-meta is supposed to be strictly xml i think
      (:content v)
      (filter #(and (= (:tag %) :Link) (= (-> % :attrs :rel) "lrdd")) v)
      (first v)
      (-> v :attrs :template)
      (str/replace v "{uri}" user)
      (http/get v))))

(defn parse-xml
  [body]
  ; xml/parse doesn't work with strings so i've gotta hack it like this
  (let [{tags :content} (-> body .getBytes io/input-stream xml/parse)]
    (reduce
      (fn [aggr tag]
        (case (:tag tag)
          :Subject (assoc aggr :subject (-> tag :content first))
          :Alias (assoc aggr :aliases
                   (conj (or (:aliases aggr) []) (-> tag :content first)))
          :Link (assoc aggr :links (conj (or (:links aggr) []) (:attrs tag)))))
      {} tags)))

(defn parse-response
  "Expects a http/get generated response map.
  Throws IllegalArgumentException if the Content-Type isn't JSON or XML."
  [response]
  (let [{body :body {type :content-type} :headers} response]
    (case (->> type (re-find #"(\w+/[\w+]+)") last)
      ("application/jrd+json" "application/json") (json/parse-string body true)
      ("application/xrd+xml" "application/xml" "text/xml") (parse-xml body))))

(defn lookup
  ([acct options] (lookup acct options (lookup-standard acct options)))
  ([acct options response]
    (let [{status :status} response]
      (case status
        200 (parse-response response)
        (lookup acct options (lookup-template acct options))))))
