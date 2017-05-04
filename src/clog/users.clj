(ns clog.users
  (require [clojure.java.jdbc :as sql]))

(def pg-db 
  "postgresql://localhost:5432/clog")

(defn save [email password]
  (sql/insert! pg-db :users
    {:email email :password password}))

(defn user-id [email]
  (:user_id (first (sql/query pg-db
    ["select user_id from users where email=?" email]))))

(defn role-id [user-id]
  (:role_id (first (sql/query pg-db
    ["select role_id from user_roles where user_id=?" user-id]))))

(defn role-for [email]
  (let [role (:name (first (sql/query pg-db
    ["select name from roles where role_id=?" (role-id (user-id email))])))]
    (if (nil? role)
      "user"
      role)))
