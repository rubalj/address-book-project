(ns address-book-project.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [address-book-project.core :as core]))
 (use '[ring.middleware.json :only [wrap-json-body]]
     '[ring.util.response :only [response]])

(defroutes app-routes
  (GET "/address" [] core/display-records)
  (GET "/index" [] core/display-index)
  (POST "/address" [] core/insert-record)
  (GET "/address/:id" [] core/retrieve-record-by-id)
  (DELETE "/address/:id" [] core/delete-record-by-id)
  (PUT "/address/:id" [] core/update-record-by-id)
  (GET "/address/search/:name" [] core/search-by-name)
  (GET "/address/trial/:name" [] (core/trial-function :name ""))
  (route/not-found "Not Found"))

(def app
  (wrap-json-body app-routes {:keywords? true}
                  (assoc-in site-defaults [:security :anti-forgery] false)))
