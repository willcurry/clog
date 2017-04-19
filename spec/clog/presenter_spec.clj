(ns clog.presenter-spec
  (:require [speclj.core :refer :all]
            [clog.presenter :refer :all]))

(describe "presenter"
  (it "should return a page with the title Clog"
    (should-contain "<title>Clog</title>" (index))))
