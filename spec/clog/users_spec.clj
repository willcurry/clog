(ns clog.users-spec
  (:require [speclj.core :refer :all]
            [clog.users :as users :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "users"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 

  (before
    (sql/insert! pg-db :users
      {:email "wcurry@8thlight.com"})
    (sql/insert! pg-db :roles
      {:name "admin"})
    (sql/insert! pg-db :permissions
      {:permission_id "a" :name "all"})
    (sql/insert! pg-db :user_roles
      {:user_id (users/user-id "wcurry@8thlight.com") :role_id (users/role-id-from "admin")})
    (sql/insert! pg-db :role_permissions
      {:permission_id "a" :role_id (users/role-id (users/user-id "wcurry@8thlight.com"))}))

  (after
    (sql/db-do-commands pg-db "truncate table user_roles")
    (sql/db-do-commands pg-db "truncate table role_permissions")
    (sql/db-do-commands pg-db "truncate table users cascade")
    (sql/db-do-commands pg-db "truncate table roles cascade")
    (sql/db-do-commands pg-db "truncate table permissions cascade"))
  
  (it "saves users"
    (users/save "test@test.com")
    (should= 1
      (count (sql/query pg-db
        ["select * from users where email='test@test.com'"]))))
  
  (it "gets a users role from their email"
    (should= "admin"
      (users/role-for "wcurry@8thlight.com"))))
