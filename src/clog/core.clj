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
  (save-blog (first parameters))
  (response (index)))

(defn- blog-view-request [parameters]
  (let [id (read-string (first parameters))
        blog (get-blog id)]
  (response (blog-view blog))))

(defn- handle [request]
  (let [query-params (first (:query-params request))
        page (first query-params)
        parameters (rest query-params)]
    (cond 
      (= page "save") (save-request parameters)
      (= page "view") (blog-view-request parameters)
      :else (response (index)))))

(def app
  (-> handle
    (wrap-params)
    (wrap-resource "public")))
