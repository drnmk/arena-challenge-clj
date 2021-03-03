(ns arena-challenge-clj.b
  (:require [clojure.java.io :as io]
            [clojure.string :as cstr]))

(defn adder [x y] (+ x y))

(def source-file
  "Artist_lists_small.txt")

(defn get-source-string [source-file]
  (-> source-file 
      io/resource
      io/file
      slurp))

;;;;
(def source-string (time (get-source-string source-file)))
;; "Elapsed time: 15.691381 msecs"

(defn get-full-lines [source-string]
  (as-> source-string ss
    (cstr/split ss #"\n")
    (map #(cstr/split % #",") ss)))

;;;;
(def full-lines (time (get-full-lines source-string)))
;; "Elapsed time: 8.824566 msecs"

(defn name-appear-in-fifty-lines?
  "this function check a name
  is included in more than 50 lines."
  [name]
  (as-> full-lines v
    (filter (fn [l] (some #(= name %) l )) v)
    (count v)
    (< 50 v)))

(defn extract-essential-names
  "names is a collection of names
  that appear in more than 50 lines."
  [full-lines]
  (let [all-names (apply concat full-lines)]
    (filter name-appear-in-fifty-lines? all-names)))

;;;;
(def essential-names (time (extract-essential-names full-lines)))
;; "Elapsed time: 0.257785 msecs"
(first essential-names) ;; => "Regina Spektor"
(type essential-names) ;; => clojure.lang.LazySeq
;; (count essential-names) ;; => 121 ;; takes long


(defn slimdown-line [full-line essential-names]
  (clojure.set/intersection
   (set full-line)
   (set essential-names)))

(defn slimdown-lines [full-lines essential-names]
  (map #(slimdown-line % essential-names) full-lines))

(def essential-lines
  (time (slimdown-lines full-lines essential-names)))
;; "Elapsed time: 0.389364 msecs"
(type essential-lines) ;; => clojure.lang.LazySeq
(first essential-lines)


(def pairs
  "is a collection of pairs of names
  where the two names in the pair aren't the same."
  (let [all-pairs (for [a names
                        b names]
                    [a b])]
    (filter #(not= (first %) (second %)) all-pairs)))

(defn pair-included-in-fifty-lines?
  "checks wheter a pair in more than fifty lines."
  [pair]
  (< 50 
     (count 
      (filter (fn [l] (and (some #(= (first pair) %) l)
                           (some #(= (second pair) %) l))) lines))))

(def final-answer
  (filter pair-included-in-fifty-lines? pairs))
