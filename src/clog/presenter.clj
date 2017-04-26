(ns clog.presenter
  (:require [hiccup.page :as page]
            [clog.markdown :as markdown]
            [clog.blogs :as blogs]
            [clog.users :as users]))

(defn- create-page [body]
  (page/html5
      [:head
        [:script {:src "https://apis.google.com/js/platform.js" :async "async" :defer "defer" }]
        [:meta {:name "google-signin-client_id" :content "862198892066-45b2o1fbgbf8v6n4r89pe3gitiab63di.apps.googleusercontent.com"}]
        [:title "Clog"]
        (page/include-css "css/style.css")
        (page/include-js "js/google.js")]
      [:body 
        [:header 
          [:div {:class "nav"}
            [:ul
              [:li [:a {:href "/"} "Home"]
              [:li [:a {:href "https://github.com/willcurry/clog"} "GitHub"]]]]]
              [:div {:class "g-signin2" :data-onsuccess "onSignIn"}]
              [:a {:href "#" :onclick "signOut();"} "Sign out"]
      body]]))

(defn index []
  (create-page [:div [:h1 "Blogs"]
    (map (fn [blog]
      [:figure {:class "snip1369 green"}
      [:img {:src "https://s3-us-west-2.amazonaws.com/s.cdpn.io/331810/pr-sample15.jpg", :alt "pr-sample15"}]
      [:div {:class "image"}]
      [:figcaption  
       [:h3 (:date blog)]
        [:p (blogs/preview-text (:id blog))]]
      [:span {:class "read-more"} "\n    Read More " 
       [:i {:class "ion-android-arrow-forward"}]]
      [:a {:href (str "?view=" (:id blog))}]]) 
    (blogs/all))
    [:form 
      [:textarea {:rows "5", :cols "60", :name "save"}]
      [:br]
      [:input  {:type "submit"}]]]))

(defn blog-view [blog]
  (create-page [:div {:class "blog"}
        [:h1 (:date blog)]
        [:p (markdown/parse (:blog blog))]
        [:a {:href (str "?edit=" (:id blog))} "Edit"]]))

(defn edit-view [blog]
  (create-page [:div {:class "blog"}
    [:h1 (:date blog)]
    [:form
      [:textarea {:rows "5" :cols "60" :name "update"} (:blog blog)]
      [:br]
      [:input {:type "hidden" :name "id" :value (:id blog)}]
      [:input  {:type "submit"}]]]))

(defn four-oh-four []
  (create-page [:div {:class "blog"}
    [:h1 "404"]]))
