(ns clog.core-spec
  (:require [speclj.core :refer :all]
            [clog.permissions :as permissions :refer :all]))

(describe "permissions"
  (it "can determine if a user can read"
    (should= true (permissions/can-perform? "read" "user")))

  (it "assumes the role is user if an invalid one is given"
    (should= true (permissions/can-perform? "read" "test")))
  
  (it "can determine if a user can update"
    (should= false (permissions/can-perform? "update" "user")))
  
  (it "knows admin has all permissions"
    (should= true (permissions/can-perform? "update" "admin"))
    (should= true (permissions/can-perform? "save" "admin"))))
