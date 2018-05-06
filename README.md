# Vuk [![Build Status](https://travis-ci.com/valerauko/vuk.svg?branch=master)](https://travis-ci.com/valerauko/vuk)

A Clojure library to handle WebFinger interactions for you.

## Usage

### Finger
```
(finger "acct:valerauko@pawoo.net")
```
Fetches the WebFinger representation of the given user then parses it into a hashmap.

If any errors occur (404 not found, unsupported scheme, invalid JSON/XML) in the process, the used libraries' errors are thrown as-is.

Properties are not supported yet.

### Links
```
(let [user (finger "acct:valerauko@pawoo.net")]
  (link user "self"))
; or even
(-> "acct:valerauko@pawoo.net" finger (link "self"))
```
It returns the first link node with a `rel` matching the given parameter.

### Represent
```
(def user-map
  {:subject "acct:valerauko@pawoo.net"
   :aliases ["https://pawoo.net/@valerauko" "https://pawoo.net/users/valerauko"]
   :links [{:rel "http://webfinger.net/rel/profile-page"
            :type "text/html"
            :href "https://pawoo.net/@valerauko"}
           {:rel "self"
            :type "application/activity+json"
            :href "https://pawoo.net/users/valerauko"}]})
(represent user-map)
(represent user-map :as :json)
(represent user-map :as :xml)
```
Returns a string representing the user as WebFinger JSON or XML. You can pass the format in with `:as` (it defaults to JSON).

## Todo
* put on Clojars
* more tests
* support for `properties`

## License

Copyright © 2018 @[valerauko](https://github.com/valerauko)

Distributed under the Eclipse Public License either version 2.0 or (at your option) any later version.
