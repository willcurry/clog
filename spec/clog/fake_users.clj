(ns clog.fake-users
  (:require [clog.users :as users :refer :all]
            [clojure.java.jdbc :as sql]))

(defn- role-id-from-name [name]
  (:role_id (first (sql/query pg-db
    ["select role_id from roles where name=?" name]))))

(defn create-fake-data []
    (sql/insert! pg-db :users
      {:email "wcurry@8thlight.com"})
    (sql/insert! pg-db :roles
      {:name "admin"})
    (sql/insert! pg-db :permissions
      {:permission_id "a" :name "all"})
    (sql/insert! pg-db :user_roles
      {:user_id (users/user-id "wcurry@8thlight.com") :role_id (role-id-from-name "admin")})
    (sql/insert! pg-db :role_permissions
      {:permission_id "a" :role_id (users/role-id (users/user-id "wcurry@8thlight.com"))}))

(defn clear-tables []
  (sql/db-do-commands pg-db "truncate table user_roles")
  (sql/db-do-commands pg-db "truncate table role_permissions")
  (sql/db-do-commands pg-db "truncate table users cascade")
  (sql/db-do-commands pg-db "truncate table roles cascade")
  (sql/db-do-commands pg-db "truncate table permissions cascade"))
