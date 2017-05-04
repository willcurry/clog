(ns clog.permissions-spec
  (:require [speclj.core :refer :all]
            [clog.users :as users :refer :all]
            [clog.permissions :as permissions :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "permissions"
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

  (it "can determine if a user can read"
    (should= true (permissions/can-perform? "read" "user@8thlight.com")))

  (it "assumes the role is user if an invalid one is given"
    (should= true (permissions/can-perform? "read" "test")))
  
  (it "can determine if a user can update"
    (should= false (permissions/can-perform? "update" "user@8thlight.com")))
  
  (it "knows admin has all permissions"
    (should= true (permissions/can-perform? "update" "wcurry@8thlight.com"))
    (should= true (permissions/can-perform? "save" "wcurry@8thlight.com"))))
