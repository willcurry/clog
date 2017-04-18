(ns clog.blogs
  (:require [clojure.java.jdbc :as sql]))

(def pg-db 
  "postgresql://localhost:5432/clog") 

(defn save-blog [blog]
  (sql/insert! pg-db :blogs
    {:blog blog}))

(defn all-blogs []
  (sql/query pg-db
    ["select * from blogs"]))

(defn get-blog [id]
  (first (sql/query pg-db
    ["select * from blogs where id=?" id])))

(defn preview-blog [id]
   (let [blog (:blog (first (sql/query pg-db
    ["select * from blogs where id=?" id])))]
      (clojure.string/split blog #" " 10)))
