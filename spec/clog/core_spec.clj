(ns clog.core-spec
  (:require [speclj.core :refer :all]
            [clog.core :refer :all]
            [clog.blogs :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "core"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 

  (after
    (sql/db-do-commands pg-db "truncate table blogs"))

  (it "returns a 200 response after a valid request"
    (should= 200 (:status (app {:query-params [""]}))))

  (it "saves the given blog"
    (app {:query-params [["save" "test"]]})
    (should= "test"
      (:blog (first (sql/query pg-db
        ["select * from blogs where blog='test'"])))))
  
  (it "goes to the blog view when requested" 
    (save-blog "test-blog")
    (let [id (str (:id (first (all-blogs))))]
      (should-contain "<p>test-blog</p>"
        (:body (app {:query-params [["view" id]]})))))
  
  (it "goes to edit the blog when requested"
    (save-blog "test-blog")
      (let [id (str (:id (first (all-blogs))))]
        (should-contain "test-blog</textarea>" 
          (:body (app {:query-params [["edit" id]]})))))

  (it "updates blog when requested"
    (save-blog "test-blog")
      (let [id (:id (first (all-blogs)))]
        (app {:query-params [["update" "test"] ["id" (str id)]]})
        (should= "test" (:blog (get-blog id))))))
