(ns clog.core-spec
  (:require [speclj.core :refer :all]
            [clog.core :refer :all]
            [clojure.java.jdbc :as sql]))

(describe "core"
  (around [it] (with-redefs [pg-db "postgresql://localhost:5432/clog_test"] (it))) 

  (after
    (sql/db-do-commands pg-db "truncate table blogs"))

  (it "returns a page with the title of clog"
    (should-contain "<title>Clog</title>" (:body (app {:query-params [""]}))))

  (it "saves a blog"
    (app {:query-params ["save" "test"]})
    (should= {:blog "test"}
      (first (sql/query pg-db
        ["select * from blogs where blog='test'"])))))
