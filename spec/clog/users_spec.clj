(ns clog.users-spec
  (:require [speclj.core :refer :all]
            [clog.users :as users :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "users"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 

  (after
    (sql/db-do-commands pg-db "truncate table user_roles")
    (sql/db-do-commands pg-db "truncate table role_permissions")
    (sql/db-do-commands pg-db "truncate table users cascade")
    (sql/db-do-commands pg-db "truncate table roles cascade")
    (sql/db-do-commands pg-db "truncate table permissions cascade"))

  (before
    (sql/insert! pg-db :users
      {:user_id 1 :email "wcurry@8thlight.com"})
    (sql/insert! pg-db :roles
      {:role_id 1 :name "admin"})
    (sql/insert! pg-db :permissions
      {:permission_id 1 :name "all"})
    (sql/insert! pg-db :user_roles
      {:user_id 1 :role_id 1})
    (sql/insert! pg-db :role_permissions
      {:permission_id 1 :role_id 1}))
  
  (it "saves users"
    (should= 1
      (count (sql/query pg-db
        ["select * from users where email='wcurry@8thlight.com'"]))))
  
  (it "gets a users role from their email"
    (should= "admin"
      (users/role-for "wcurry@8thlight.com"))))
