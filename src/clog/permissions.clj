(ns clog.permissions)

(def roles
  {:admin {:name "admin" :flags "*"}
   :user {:name "user" :flags ["read"]}})

(defn get-role [role-string]
  (let [role ((keyword role-string) roles)]
    (if (nil? role)
      (:user roles)
      role)))

(defn can-perform? [action role]
  (= action (some #{action} (:flags role))))
