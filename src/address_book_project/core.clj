(ns address-book-project.core
  (:require [cheshire.core :as cs]
            [address-book-project.schema :as schema]
            [schema.core :as sc :include-macros true])
  (:import java.util.UUID))


(defonce book (atom {}))


(defn gen-id
  "Returns a random ID"
  []
  (str (UUID/randomUUID)))


(defn insert-record
  "Inserts a new record into the addressbook after a POST request. The details to be inserted are passed in JSON format"
  [request]
  (let [id (gen-id)
        recordvalue (assoc (:body request) :id id)]
    ;;(swap! book assoc id recordvalue)
    (swap! book assoc id recordvalue)
    {:status 200
     :headers {"content-type" "application/json"}
     :body (cs/generate-string {:id id})}))


(defn display-records
  "Displays all the records present in the addressbook"
  [request]
  {:status 200
   :headers {"content-type" "application/json"}
   :body (cs/generate-string (vals @book) {:pretty true})})


(defn retrieve-record-by-id
  "Searches the addressbook with the ID passed and returns the corresponding record if it exists in the addressbook"
  [request]
  (let [id (:id (:params request))]

    {:status 200
     :headers {"content-type" "application/json"}
     :body (cs/generate-string (get @book id) {:pretty true})})) 


(defn delete-record-by-id
  "Deletes a record corresponding to the ID passed, if exists"
  [request]
  (let [id (:id (:params request))]
    (swap! book dissoc id))
  {:status 200})


(defn update-record-by-id
  "Updates a record with the details passed in a PUT request, if the record exists"
  [request]
  (let [id (:id (:params request))]
    (swap! book update-in [id] merge (:body request)))
  {:status 200})

