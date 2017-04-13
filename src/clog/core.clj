(ns clog.core
  (use [ring.adapter.jetty]
       [hiccup.core]
       [ring.middleware.params]
       [ring.middleware.resource]
       [clog.presenter]
       [clog.blogs]))

(defn- response [page]
  {:status 200
   :headers {}
   :body page})

(defn- save-request [parameters]
  (save-blog (first parameters)))

(defn- handle [request]
  (let [query-params (first (:query-params request))
    page (first query-params)
    parameters (rest query-params)]
    (cond 
      (= page "save") (save-request parameters))
      (response (index))))

(def app
  (-> handle
    (wrap-params)
    (wrap-resource "public")))
