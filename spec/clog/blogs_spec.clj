(ns clog.blogs-spec
  (:require [speclj.core :refer :all]
            [clog.blogs :as blogs :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "blogs"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 

  (after
    (sql/db-do-commands pg-db "truncate table blogs"))

  (defn- id-of-first-blog []
    (:id (first (blogs/all))))

  (it "saves a blog"
    (blogs/save "test")
    (should= "test"
      (:blog (first (sql/query pg-db
        ["select * from blogs where blog='test'"])))))

  (it "saves multiple blogs"
    (blogs/save "test")
    (blogs/save "test")
    (should= 2 (count (blogs/all))))
  
  (it "can get a blog with its id"
    (blogs/save "test-blog")
    (should= "test-blog" (:blog (blogs/retrieve (id-of-first-blog)))))
  
  (it "returns a 10 word preview"
    (blogs/save "this is a test blog consisting of 11 words only 11")
        (should= 50 (count (blogs/preview-text (id-of-first-blog)))))
  
  (it "adds ... to the preview if its over 150 chars"
    (blogs/save (apply str (map (fn [x] "test ") (range 40))))
    (should-contain "..." (blogs/preview-text (id-of-first-blog))))
  
  (it "can update blogs"
    (blogs/save "test")
    (blogs/update (id-of-first-blog) "test2")
    (should= "test2" (:blog (blogs/retrieve (id-of-first-blog))))))
