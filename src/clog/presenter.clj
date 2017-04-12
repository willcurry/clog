(ns clog.presenter
  (:require [hiccup.page :as page]))

(defn index []
  (page/html5
    [:head
      [:title "Clog"]]
    [:body
      [:form {:action "/save"}
      [:textarea {:rows "4", :cols "50", :name "blog-post"}]
      [:br]
      [:input {:type "submit"}]]]))
