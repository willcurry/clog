(ns clog.blogs
  (require [clojure.java.jdbc :as sql]))

(def pg-db 
  "postgresql://localhost:5432/clog") 

(defn save [blog]
  (sql/insert! pg-db :blogs
    {:blog blog}))

(defn all []
  (sql/query pg-db
    ["select * from blogs"]))

(defn retrieve [id]
  (first (sql/query pg-db
    ["select * from blogs where id=?" id])))

(defn preview-text [id]
   (let [blog (:blog (first (sql/query pg-db
    ["select * from blogs where id=?" id])))]
      (str (subs blog 0 (min (count blog) 150)) 
        (if (> (count blog) 150) "..."))))

(defn update [id text]
  (sql/execute! pg-db
    ["update blogs SET blog=? WHERE id=?" text id]))
