(ns clog.core
  (use [ring.adapter.jetty]
       [hiccup.core])
  (:require [hiccup.page :as page]))

(defn- page []
  (page/html5
    [:head
      [:title "Clog"]]
    [:body
      [:div "Hello and welcome to Clog"]]))

(defn -main
  [& args]
  (println "Hello World"))

(defn handle [request]
  {:status 200
   :headers {}
   :body (page)})
