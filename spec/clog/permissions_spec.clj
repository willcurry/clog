(ns clog.permissions-spec
  (:require [speclj.core :refer :all]
            [clog.users :as users :refer :all]
            [clog.permissions :as permissions :refer :all]
            [clog.fake-users :as fake-users :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "permissions"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 

  (before
    (fake-users/create-fake-data))

  (after
    (fake-users/clear-tables))

  (it "can determine if a user can read"
    (should= true (permissions/can-perform? "read" "user@8thlight.com")))

  (it "assumes the role is user if an invalid one is given"
    (should= true (permissions/can-perform? "read" "test")))
  
  (it "can determine if a user can update"
    (should= false (permissions/can-perform? "update" "user@8thlight.com")))
  
  (it "knows admin has all permissions"
    (should= true (permissions/can-perform? "update" "wcurry@8thlight.com"))
    (should= true (permissions/can-perform? "save" "wcurry@8thlight.com"))))
