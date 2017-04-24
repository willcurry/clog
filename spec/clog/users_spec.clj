(ns clog.users-spec
  (:require [speclj.core :refer :all]
            [clog.users :as users :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "users"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 

  (after
    (sql/db-do-commands pg-db "truncate table users"))
  
  (it "saves users"
    (users/save "wcurry@8thlight.com" "admin")
    (should= 1
      (count (sql/query pg-db
        ["select * from users where email='wcurry@8thlight.com'"])))))
