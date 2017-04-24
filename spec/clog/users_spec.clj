(ns clog.users-spec
  (:require [speclj.core :refer :all]
            [clog.users :as users :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "users"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 
  
  (it "saves users"
    (should= 1
      (count (sql/query pg-db
        ["select * from users where email='wcurry@8thlight.com'"]))))
  
  (it "gets a users role from their email"
    (should= "admin"
      (users/role-for "wcurry@8thlight.com"))))
