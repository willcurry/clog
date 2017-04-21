(ns clog.core-spec
  (:require [speclj.core :refer :all]
            [clog.permissions :as permissions :refer :all]))

(describe "permissions"
  (it "can determine role from query string values"
    (should="admin" (permissions/get-role ["view" "admin"])))

  (it "role defaults to user if no other role is present"
    (should="user" (permissions/get-role ["view"]))))
