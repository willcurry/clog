(ns clog.users-spec
  (:require [speclj.core :refer :all]
            [clog.users :as users :refer :all]
            [clog.fake-users :as fake-users :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "users"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 

  (before
    (fake-users/create-fake-data))

  (after
    (fake-users/clear-tables))
  
  (it "saves users"
    (users/save "test@test.com" "pw")
    (should= 1
      (count (sql/query pg-db
        ["select * from users where email='test@test.com'"]))))
  
  (it "gets a users role from their email"
    (should= "admin"
      (users/role-for "wcurry@8thlight.com"))))
