(ns clog.core
  (require [ring.middleware.params :as params]
       [ring.middleware.resource :as resource]
       [clog.presenter :as presenter]
       [clog.blogs :as blogs]
       [clog.permissions :as permissions]))

(defn- response [page]
  {:status 200
   :headers {}
   :body page})

(defn- save-blog-request [{save-params :query-values email :email}]
  (if (permissions/can-perform? "save" email)
    (blogs/save (first save-params)))
  (response (presenter/index)))

(defn- blog-view-request [{view-params :query-values}]
  (let [id (read-string (first view-params))
        blog (blogs/retrieve id)]
  (response (presenter/blog-view blog))))

(defn- edit-view-request [{edit-params :query-values}]
  (let [id (read-string (first edit-params))
        blog (blogs/retrieve id)]
  (response (presenter/edit-view blog))))

(defn- update-blog-request [{update-params :query-values email :email}]
  (let [text (first update-params)
        id (read-string (second update-params))]
    (if (permissions/can-perform? "update" email)
      (blogs/update id text))
    (response (presenter/blog-view (blogs/retrieve id)))))

(def requests
  {:save save-blog-request
  :view blog-view-request
  :edit edit-view-request
  :update update-blog-request})

(defn- parse-request [request]
  (let [page (keyword (first (keys (:query-params request))))]
      {:callback (get requests page)
       :query-values (vals (:query-params request))
       :email nil}))

(defn- handler [request]
  (let [parsed-request (parse-request request)
        callback (:callback parsed-request)]
        (if (nil? callback)
            (response (presenter/index))
            (callback parsed-request))))

(def app
  (-> handler
    (params/wrap-params)
    (resource/wrap-resource "public")))
