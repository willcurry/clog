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
    (should= "test"
      (:blog (first (sql/query pg-db
        ["select * from blogs where blog='test'"])))))

  (it "saves multiple blogs"
    (save-blog "test")
    (save-blog "test")
    (should= 2 (count (all-blogs))))
  
  (it "can get a blog with its id"
    (save-blog "test-blog")
    (let [id (:id (first (all-blogs)))]
      (should= "test-blog" (:blog (get-blog id)))))
  
  (it "returns a 10 word preview"
    (save-blog "this is a test blog consisting of 11 words only 11")
      (let [id (:id (first (all-blogs)))]
        (should= 10 (count (preview-blog id))))))
