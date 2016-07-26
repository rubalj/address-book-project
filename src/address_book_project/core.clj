(ns address-book-project.core
  (:require [cheshire.core :as cs])
  (:import java.util.UUID))


(defonce book (atom {}))


(defn gen-id
  []
  (str (UUID/randomUUID)))


(defn insert-record
  [request]
  (let [id (gen-id)
        recordvalue (assoc (:body request) :id id)]
    ;;(swap! book assoc id recordvalue)
    (swap! book assoc id recordvalue)
    (cs/generate-string {:id id})))


(defn display-records
  [request]
  (cs/generate-string (vals @book) {:pretty true}))


(defn retrieve-record-by-id
  [request]
  (let [id (:id (:params request))]

    {:status 200
     :headers {"content-type" "application/json"}
     :body (cs/generate-string (get @book id) {:pretty true})})) 


(defn delete-record-by-id
  [request]
  (let [id (:id (:params request))]
    (swap! book dissoc id)))


(defn update-record-by-id
  [request]
  (update-in @book [:1] assoc :value 1 :active true))

