(ns vuk.response
  (:require [cheshire.core :as json]
            [clojure.data.xml :as xml]))

(defn represent-xml
  "Prints a user's representation as XML.
  Expects input to be the format of core/finger's output."
  [{:keys [subject aliases links] :or {aliases [] links []}}]
  (xml/emit-str
    (xml/element :XRD { :xmlns "http://docs.oasis-open.org/ns/xri/xrd-1.0" }
      (conj [(xml/element :Subject {} subject)]
        (map #(xml/element :Alias {} %) aliases)
        (map #(xml/element :Link % "") links)))))

(defn represent-json
  "Prints a user's representation as JSON.
  Expects input to be the format of core/finger's output."
  [hashmap]
  (json/generate-string hashmap))