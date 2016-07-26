(ns address-book-project.schema
  (:require [schema.core :as s :include-macros true]))


(def validation-pattern
  "A schema for a nested data type"
  {:name s/Str
   :phone s/Int
   :email s/Str})