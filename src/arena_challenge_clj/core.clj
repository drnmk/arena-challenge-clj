(ns arena-challenge-clj.core
  (:require
   [clojure.java.io :as io]
   [clojure.data.csv :as csv]
   [arena-challenge-clj.functions :refer :all]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; (def source-string (get-source-string source-file))
;; (def lines (get-lines source-string))
;; (first lines)
;; (def essential-names (extract-essential-names lines))
;; (first essential-names)
;; (def candidate-pairs (extract-candidate-pairs essential-names))
;; (first candidate-pairs)
;; (def pairs-appearing-fifty-times (extract-pairs-appearing-fifty-times candidate-pairs lines))
;; (def sample (take 5 pairs-appearing-fifty-times))


(def source-file "Artist_lists_small.txt")
(def result-file "output/result.csv")

(defn -main [] 
  (with-open [writer (io/writer result-file)]
    (csv/write-csv writer (time (get-answer source-file)))))
