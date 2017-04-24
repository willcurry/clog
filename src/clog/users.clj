(ns clog.users
  (require [clojure.java.jdbc :as sql]))

(def pg-db 
  "postgresql://localhost:5432/clog")

(defn save [email role]
  (sql/insert! pg-db :users
    {:email email :role role}))
