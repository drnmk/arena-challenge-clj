(ns arena-challenge-clj.a
  (:require [clojure.java.io :as io]
            [clojure.string :as cstr]))

(def source-file
  "Artist_lists_small.txt")

(def source
  "source is a big string
  captured from the entire source file."
  (-> source-file 
      io/resource
      io/file
      slurp))

(def lines
  "lines is the collection of line items.
  each line has multiple names.
  vectors within a vector."
  (as-> source v
    (cstr/split v #"\n")
    (map #(cstr/split % #",") v)))

(defn name-included-in-fifty-lines?
  "this function check a name
  is included in more than 50 lines."
  [name]
  (as-> lines v
    (filter (fn [l] (some #(= name %) l )) v)
    (count v)
    (< 50 v)))

(def names
  "names is a collection of names
  that appear in more than 50 lines."
  (let [all-names (set (apply concat lines))]
    (filter name-included-in-fifty-lines? all-names)))

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
