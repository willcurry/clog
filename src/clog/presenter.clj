(ns clog.presenter
  (use [clog.blogs])
  (:require [hiccup.page :as page]))

(defn index []
  (page/html5
    [:head
      [:title "Clog"]
      (page/include-css "css/style.css")]
    [:body
      [:h1 "Blogs"]
      (map (fn [blog]
        [:figure {:class "snip1369 green"}
        [:img {:src "https://s3-us-west-2.amazonaws.com/s.cdpn.io/331810/pr-sample15.jpg", :alt "pr-sample15"}]
        [:div {:class "image"}]
        [:figcaption  
         [:h3 (:date blog)]
          [:p (preview-blog (:id blog))]]
        [:span {:class "read-more"} "\n    Read More " 
         [:i {:class "ion-android-arrow-forward"}]]
        [:a {:href (str "?view=" (:id blog))}]]) 
      (all-blogs))
      [:form 
      [:textarea {:rows "5", :cols "60", :name "save"}]
      [:br]
      [:input  {:type "submit"}]]]))

(defn blog-view [blog]
  (page/html5
    [:head
      [:title "Clog"]
      (page/include-css "css/style.css")]
    [:body
      [:h1 (:date blog)]
      [:p (:blog blog)]]))
