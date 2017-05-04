;; migrations/20170504134348282-add-password-column.clj

(defn up []
  ["ALTER TABLE users ADD password text NOT NULL"])

(defn down []
  ["ALTER TABLE users DROP COLUMN text"])
