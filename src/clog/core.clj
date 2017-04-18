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

(defn- save-blog-request [parameters]
  (save-blog (first parameters))
  (response (index)))

(defn- blog-view-request [parameters]
  (let [id (read-string (first parameters))
        blog (get-blog id)]
  (response (blog-view blog))))

(defn- edit-view-request [parameters]
  (let [id (read-string (first parameters))
        blog (get-blog id)]
  (response (edit-view blog))))

(defn- update-blog-request [parameters secondary-parameters]
  (let [text (first parameters)
        id (read-string (first secondary-parameters))]
    (update-blog id text)
    (response (blog-view (get-blog id)))))

(defn- handle [request-data]
  (let [data (first (:query-params request-data))
        request (first data)
        parameters (rest data)
        secondary-parameters (rest (second (:query-params request-data)))]
    (cond 
      (= request "save") (save-blog-request parameters)
      (= request "view") (blog-view-request parameters)
      (= request "edit") (edit-view-request parameters)
      (= request "update") (update-blog-request parameters secondary-parameters)
      :else (response (index)))))

(def app
  (-> handle
    (wrap-params)
    (wrap-resource "public")))
