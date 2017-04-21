(ns clog.core-spec
  (:require [speclj.core :refer :all]
            [clog.permissions :as permissions :refer :all]))

(describe "permissions"
  (it "can determine a role"
    (should="admin" (:name (permissions/get-role "admin"))))

  (it "role defaults to user if no other role is present"
    (should="user" (:name (permissions/get-role "view"))))
  
  (it "can determine if a user can read"
    (should= true (permissions/can-perform? "read" (permissions/get-role "user"))))
  
  (it "can determine if a user can update"
    (should= false (permissions/can-perform? "update" (permissions/get-role "user")))))
