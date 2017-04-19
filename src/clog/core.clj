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

(defn- save-blog-request [[request-params]]
  (save-blog (second request-params))
  (response (index)))

(defn- blog-view-request [[request-params]]
  (let [id (read-string (second request-params))
        blog (get-blog id)]
  (response (blog-view blog))))

(defn- edit-view-request [[request-params]]
  (let [id (read-string (second request-params))
        blog (get-blog id)]
  (response (edit-view blog))))

(defn- update-blog-request [[update-params id-params]]
  (let [text (second update-params)
        id (read-string (second id-params))]
    (update-blog id text)
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
      (response index)
      (callback query-data))))

(def app
  (-> handler
    (wrap-params)
    (wrap-resource "public")))
