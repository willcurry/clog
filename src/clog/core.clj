(ns clog.core
  (use [ring.adapter.jetty]
       [hiccup.core]
       [ring.middleware.params])
  (:require [hiccup.page :as page]))

(defn- page []
  (page/html5
    [:head
      [:title "Clog"]]
    [:body
      [:form {:action "/save"}
      [:textarea {:rows "4", :cols "50", :name "blog-post"}]
      [:br]
      [:input {:type "submit"}]]]))

(defn handle [request]
  {:status 200
   :headers {}
   :body (page)})

(def app
  (wrap-params handle))
