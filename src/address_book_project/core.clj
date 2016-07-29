(ns address-book-project.core
  (:require [cheshire.core :as cs]
            [address-book-project.schema :as schema]
            [schema.core :as sc :include-macros true])
  (:import java.util.UUID))


(defonce book (atom {}))
(defonce nameindex (atom {}))

(defn gen-id
  "Returns a random ID"
  []
  (str (UUID/randomUUID)))


(defn trial-function
  [keyvalue namevalue]
  (let [names (map (fn [[id value]] (keyvalue value)) @book )
        name namevalue]
    (some (fn [x] (= x name)) names)))


(defn insert-record
  "Inserts a new record into the addressbook after a POST request. The details to be inserted are passed in JSON format."
  [request]
  (let [id (gen-id)
        recordvalue (assoc (:body request) :id id)
        name (:name (:body request))
        email (:email (:body request))]
    (sc/validate schema/data-schema recordvalue)
    (if (or (trial-function :name name) (trial-function :email email))
      {:status 412}
      (do
        (swap! book assoc id recordvalue)
        (swap! nameindex assoc name id)
        {:status 200
         :headers {"content-type" "application/json"}
         :body (cs/generate-string {:id id})}))))


(defn display-records
  "Returns all the records present in the addressbook"
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
     :body (cs/generate-string (@book id) {:pretty true})})) 


(defn delete-record-by-id
  "Deletes a record corresponding to the ID passed, if exists"
  [request]
  (let [id (:id (:params request))]
    (swap! book dissoc id))
  {:status 200})


(defn update-record-by-id
  "Updates a record with the details passed in a PUT request, if the record exists."
  [request]
  (let [id (:id (:params request))
        recordvalue (:body request)]
    (if (sc/check schema/data-schema-for-update recordvalue)
      {:status 412}
      (do (swap! book update id merge recordvalue)
          {:status 200}))))


(defn search-by-name
  "Searches the input name and returns the record, if it exists"
  [request]
  (let [name (:name (:params request))]
    (if (get @nameindex name)
      {:status 200
       :headers {"content-type" "application/json"}
       :body (cs/generate-string (@book (get @nameindex name)))}
      {:status 404})))



  
