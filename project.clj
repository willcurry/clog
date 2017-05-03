(defproject clog "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [endophile "0.2.1"]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}}
  :plugins [[speclj "3.3.2"]
            [lein-ring "0.11.0"]
            [clj-sql-up "0.3.7"]]
  :test-paths ["spec"]
  :ring {:handler clog.core/app}
  :clj-sql-up {:database "postgresql://localhost:5432/clog_test"
               :deps [[org.postgresql/postgresql "9.4-1201-jdbc41"]]})

