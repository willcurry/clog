(ns clog.markdown-spec
  (:require [speclj.core :refer :all]
            [clog.markdown :refer :all]))

(describe "markdown parsing"
  (it "parses the markdown and returns it in hiccup form"
    (should=[[:h1 "Hello"]] (parse "# Hello"))))
