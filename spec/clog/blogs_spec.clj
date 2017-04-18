(ns clog.blogs-spec
  (:require [speclj.core :refer :all]
            [clog.blogs :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "blogs"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 

  (after
    (sql/db-do-commands pg-db "truncate table blogs"))

  (defn- id-of-first-blog []
    (:id (first (all-blogs))))

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
    (should= "test-blog" (:blog (get-blog (id-of-first-blog)))))
  
  (it "returns a 10 word preview"
    (save-blog "this is a test blog consisting of 11 words only 11")
        (should= 50 (count (preview-blog (id-of-first-blog)))))
  
  (it "adds ... to the preview if its over 150 chars"
    (save-blog "test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test")
    (should-contain "..." (preview-blog (id-of-first-blog))))
  
  (it "can update blogs"
    (save-blog "test")
    (update-blog (id-of-first-blog) "test2")
    (should= "test2" (:blog (get-blog (id-of-first-blog))))))
