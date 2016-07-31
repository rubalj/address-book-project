(defproject address-book-project "0.1.0-SNAPSHOT"
  :description "Addressbook implementation using compojure"
  :url "http://github.com/rubalj/address-book-project"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
		 [cheshire "5.6.3"]
		 [prismatic/schema "1.1.3"]
                 [ring/ring-json "0.4.0"]
		 [ring/ring-defaults "0.2.1"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler address-book-project.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
