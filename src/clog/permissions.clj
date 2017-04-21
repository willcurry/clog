(ns clog.permissions)

(def roles
  {:admin "admin"
   :user "user"})

(defn get-role [query-params]
  (let [role ((keyword (last query-params)) roles)]
    (if (nil? role)
      (:user roles)
      role)))
