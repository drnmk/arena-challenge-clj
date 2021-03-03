(ns arena-challenge-clj.core
  (:require
   [clojure.java.io :as io]
   [clojure.data.csv :as csv]
   [arena-challenge-clj.functions :refer :all]))

(def source-file "Artist_lists_small.txt")
(def result-file "output/result.csv")
(def threshold 50)

(defn -main [] 
  (with-open [writer (io/writer result-file)]
    (csv/write-csv writer (process source-file threshold))))
