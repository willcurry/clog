(ns clog.core-spec
  (:require [speclj.core :refer :all]
            [clog.core :refer :all]))

(describe "core"
  (it "returns a page with the title of clog"
    (should-contain "<title>Clog</title>" (:body (handle "fake request")))))
