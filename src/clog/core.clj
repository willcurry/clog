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

(defn- is_admin? [params]
  (some #{"admin"} params))

(defn- save-blog-request [save-params]
  (if (is_admin? save-params)
    (save-blog (first save-params)))
  (response (index)))

(defn- blog-view-request [view-params]
  (let [id (read-string (first view-params))
        blog (get-blog id)]
  (response (blog-view blog))))

(defn- edit-view-request [edit-params]
  (let [id (read-string (first edit-params))
        blog (get-blog id)]
  (response (edit-view blog))))

(defn- update-blog-request [update-params]
  (let [text (first update-params)
        id (read-string (second update-params))]
    (if (is_admin? update-params)
      (update-blog id text))
    (response (blog-view (get-blog id)))))

(defn- requests []
  {:save save-blog-request
  :view blog-view-request
  :edit edit-view-request
  :update update-blog-request})

(defn- handler [request-data]
  (let [query-data (:query-params request-data)
        callback (first (map #((keyword (first %)) (requests)) query-data))]
    (if (nil? callback)
      (response (index))
      (callback (vals query-data)))))

(def app
  (-> handler
    (wrap-params)
    (wrap-resource "public")))
