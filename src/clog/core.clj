(ns clog.core
  (use [ring.adapter.jetty]
       [hiccup.core]
       [ring.middleware.params]
       [clog.presenter]
       [clog.blogs]))

(defn- response [page]
  {:status 200
   :headers {}
   :body page})

(defn- save-request [parameters]
  (save-blog (first parameters)))

(defn- handle [request]
  (let [page (first (:query-params request))
       parameters (rest (:query-params request))]
       (cond 
        (= page "save") (save-request parameters)))
        (response index))

(def app
  (wrap-params handle))
