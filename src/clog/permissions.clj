(ns clog.permissions
  (:require [clog.users :as users]))

(def roles
  {:admin {:name "admin" :flags "*"}
   :user {:name "user" :flags ["read"]}})

(defn- get-role [role-string]
  ((keyword role-string) roles))

(defn can-perform? [action email]
  (let [role (get-role (users/role-for email))]
    (or
      (= action (some #{action} (:flags role)))
      (= "*" (:flags role)))))
