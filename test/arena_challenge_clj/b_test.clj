(ns arena-challenge-clj.b-test
  (:require
   [arena-challenge-clj.b :refer :all]
   [clojure.test :refer :all]))


;; how fast get-source-string function?
(time (get-source-string source-file))
;; "Elapsed time: 8.981208 msecs"


;; see how fast get-lines function
(time (get-lines source-string))
;; "Elapsed time: 22.478595 msecs"


(def source-string (get-source-string source-file))
(type source-string) ;; => java.lang.String



;;;; see lines look correct
(def lines (get-lines source-string))
(type lines) ;; => clojure.lang.LazySeq
(count lines) ;; => 1000 ;; favorite musical artists of 1000 users
(count (first lines)) ;; => 50 ;; each line is a list of up to 50 artists.
(first (first lines)) ;; => "Michael Bublé"

