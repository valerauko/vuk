(ns vuk.core
  (:require [vuk.request :as request]
            [vuk.response :as response]))

(defn finger
  "The main function to look up an 'acct'."
  ([acct] (finger acct {}))
  ([acct options]
    (request/lookup acct options)))

(defn link
  ; REVIEW: i'm assuming that rels are supposed to be unique.
  ; if there can be multiple of the same rel, this should be changed
  ; to return an array of matching rels
  "Extracts a link from a finger result based on its 'rel'."
  [result rel]
  (->> result :links (filter #(= (:rel %) rel)) first))

(defn represent
  "Represents the user as either JSON or XML."
  [user & {format :as, :or {format :json}}]
  (case format
    (:json :JSON "json" "JSON") (response/represent-json user)
    (:xml :XML "xml" "XML") (response/represent-xml user)))
