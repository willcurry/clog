(ns clog.presenter-spec
  (:require [speclj.core :refer :all]
            [clog.presenter :refer :all]))

(describe "presenter"
  (it "should return a page with the title Clog"
    (should-contain "<title>Clog</title>" (index)))

  (it "disaply correct reading time"
    (should-contain "<h1>3 mins</h1>" (blog-view {:blog (apply str (repeat 500 "Hello "))}))))
