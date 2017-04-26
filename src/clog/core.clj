(ns clog.core
  (require [ring.middleware.params :as params]
       [ring.middleware.resource :as resource]
       [clog.presenter :as presenter]
       [clog.blogs :as blogs]
       [clog.auth :as auth]
       [clog.permissions :as permissions]))

(defn- response [page]
  {:status 200
   :headers {}
   :body page})

(defn- save-blog-request [save-params]
  (if (permissions/can-perform? "save" (last save-params))
    (blogs/save (first save-params)))
  (response (presenter/index)))

(defn- blog-view-request [view-params]
  (let [id (read-string (first view-params))
        blog (blogs/retrieve id)]
  (response (presenter/blog-view blog))))

(defn- edit-view-request [edit-params]
  (let [id (read-string (first edit-params))
        blog (blogs/retrieve id)]
  (response (presenter/edit-view blog))))

(defn- update-blog-request [update-params]
  (let [text (first update-params)
        id (read-string (second update-params))]
    (if (permissions/can-perform? "update" (last update-params))
      (blogs/update id text))
    (response (presenter/blog-view (blogs/retrieve id)))))

(defn- requests []
  {:save save-blog-request
  :view blog-view-request
  :edit edit-view-request
  :update update-blog-request})

(defn- handler [request-data]
  (let [query-data (:query-params request-data)
        callback (first (map #((keyword (first %)) (requests)) query-data))
        google-id (:form-params request-data)]
    (if (not (empty? google-id))
      (auth/auth (get (:form-params request-data) "idtoken")))
    (if (nil? callback)
      (response (presenter/index))
      (callback (vals query-data)))))

(def app
  (-> handler
    (params/wrap-params)
    (resource/wrap-resource "public")))
