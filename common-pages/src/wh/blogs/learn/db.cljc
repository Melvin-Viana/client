(ns wh.blogs.learn.db
  (:require
    [bidi.bidi :as bidi]
    [wh.util :as util]))

(def page-size 24)

(defn tag [db]
  (bidi/url-decode (get-in db [:wh.db/page-params :tag])))

(defn current-page [db]
  (or (util/parse-int (get-in db [:wh.db/query-params "page"])) 1))

(defn params [db]
  (-> {:page_number     (current-page db)
       :tag             (tag db)
       :vertical        (:wh.db/vertical db)
       ;; we want to fetch blogs from all verticals if tag is selected
       :vertical_blogs  (when-not (tag db) (:wh.db/vertical db))
       :promoted_amount 8
       :issues_amount   8}
      util/remove-nils))
