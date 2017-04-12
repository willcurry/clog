(ns clog.core
  (use [ring.adapter.jetty]
       [hiccup.core]
       [ring.middleware.params])
  (:require [hiccup.page :as page]
            [clojure.java.jdbc :as j]))

(def pg-db 
  "postgresql://localhost:5432/clog")

(defn- page []
  (page/html5
    [:head
      [:title "Clog"]]
    [:body
      [:form {:action "/save"}
      [:textarea {:rows "4", :cols "50", :name "blog-post"}]
      [:br]
      [:input {:type "submit"}]]]))

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
