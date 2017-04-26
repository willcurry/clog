(ns clog.users
  (require [clojure.java.jdbc :as sql]))

(def pg-db 
  "postgresql://localhost:5432/clog")

(defn- save [email role]
  (sql/insert! pg-db :users
    {:email email :role role}))

(defn role-for [email]
  (let [role (:role (first (sql/query pg-db
    ["select role from users where email=?" email])))]
    (if (nil? role)
      "user"
      role)))

(defn login [email]
  (if (empty? (sql/query pg-db ["select * from users where email=?" email]))
    (save email "user")))
    
