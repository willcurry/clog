;; migrations/20170504132049058-create-blog-table.clj

(defn up []
  ["CREATE TABLE blogs(blog text NOT NULL, date date NOT NULL default CURRENT_DATE, id serial)"])

(defn down []
  ["DROP TABLE blogs"])
