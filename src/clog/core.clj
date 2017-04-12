(ns clog.core
  (use [ring.adapter.jetty]
       [hiccup.core]
       [ring.middleware.params]
       [clog.presenter])
  (:require  [clojure.java.jdbc :as j]))

(def pg-db 
  "postgresql://localhost:5432/clog")

(defn- save-blog [blog]
  (j/insert! pg-db :blogs
    {:blog blog}))

(defn- handle [request]
  (if (= (first (:query-params request)) "save") (save-blog "test"))
  {:status 200
   :headers {}
   :body (page)})

(def app
  (wrap-params handle))
