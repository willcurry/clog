(ns clog.markdown
  (:use [endophile.core :only [mp to-clj html-string]]
    [endophile.hiccup :only [to-hiccup]]
    [hiccup.core :only [html]]))

(defn- parse-markdown [text]
  (mp text))

(defn parse [text]
  (to-hiccup (parse-markdown text)))
