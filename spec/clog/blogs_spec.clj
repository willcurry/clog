(ns clog.blogs-spec
  (:require [speclj.core :refer :all]
            [clog.blogs :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "blogs"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 

  (after
    (sql/db-do-commands pg-db "truncate table blogs"))

  (it "saves a blog"
    (save-blog "test")
    (should= {:blog "test"}
      (first (sql/query pg-db
        ["select * from blogs where blog='test'"]))))

  (it "can get all blogs"
    (save-blog "test")
    (should= [{:blog "test"}] (all-blogs))))
