(ns clog.auth
  (:import 
   [com.google.api.client.googleapis.auth.oauth2
    GoogleIdToken
    GoogleIdToken$Payload
    GoogleIdTokenVerifier$Builder
    GoogleIdTokenVerifier]
    [com.google.api.client.json.jackson2.JacksonFactory]
    [com.google.api.client.http.javanet.NetHttpTransport]))

(def json-factory (com.google.api.client.json.jackson2.JacksonFactory.))

(def http-transport (com.google.api.client.http.javanet.NetHttpTransport.))

(def client-id "862198892066-45b2o1fbgbf8v6n4r89pe3gitiab63di.apps.googleusercontent.com")

(def verifier 
  (.. (GoogleIdTokenVerifier$Builder. http-transport json-factory)
      (setAudience (list client-id)) 
      (build)))

(defn- verify-token [token]
  (.verify verifier token))

(defn login [token]
  (let [id-token (verify-token token)]
    (if (nil? id-token)
        {:success false}
        {:success true :email (.getEmail (.getPayload id-token))})))
