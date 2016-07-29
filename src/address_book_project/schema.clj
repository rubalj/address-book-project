(ns address-book-project.schema
  (:require [schema.core :as s :include-macros true]))



(def email-regex #".+\@.+\..+")
(def phn-regex #"[0-9]{10}")
(def zip-regex #"[0-9]{5}")


(def data-schema
  {(s/required-key :id) s/Str
   (s/required-key :name) s/Str
   (s/required-key :email) email-regex
   (s/optional-key :phone) phn-regex
   (s/optional-key :address) {(s/required-key :house) s/Str
                              (s/required-key :apartment) s/Str
                              (s/required-key :city) s/Str
                              (s/required-key :state) s/Str
                              (s/required-key :zip) zip-regex}})

(def data-schema-for-update
  {(s/optional-key :phone) phn-regex
   (s/optional-key :address) {(s/required-key :house) s/Str
                              (s/required-key :apartment) s/Str
                              (s/required-key :city) s/Str
                              (s/required-key :state) s/Str
                              (s/required-key :zip) zip-regex}})
