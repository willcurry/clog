(ns clog.presenter
  (use [clog.blogs])
  (:require [hiccup.page :as page]))

(defn index []
  (page/html5
    [:head
      [:title "Clog"]]
    [:body
      [:form 
      [:textarea {:rows "4", :cols "50", :name "save"}]
      [:br]
      [:input {:type "submit"}]]
      [:h1 "Blogs"]
      [:div
        (map (fn [blog] [:h3 (:blog blog)]) (all-blogs))]]))
