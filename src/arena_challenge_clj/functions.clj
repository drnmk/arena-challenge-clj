(ns arena-challenge-clj.functions
  (:require [clojure.java.io :as io]
            [clojure.string :as cstr]))

(defn get-source-string
  "source is a big string
  captured from the entire source file."
  [source-file]
  (-> source-file 
      io/resource
      io/file
      slurp))

(defn get-lines
  "lines is the collection of line items.
  each line has multiple names.
  vectors within a vector."
  [source-string]
  (as-> source-string v
    (cstr/split v #"\n")
    (map #(cstr/split % #",") v)))

(defn name-appears-in-fifty-lines?
  "this function check a name
  is included in more than 50 lines."
  [name lines]
  (as-> lines v
    (filter (fn [l] (some #(= name %) l )) v)
    (count v)
    (< 50 v)))

(defn extract-essential-names
  [lines]
  (let [all-names (set (apply concat lines))]
    (filter #(name-appears-in-fifty-lines? % lines) all-names)))

(defn extract-candidate-pairs
  [essential-names]
  (let [all-pairs (for [a essential-names b essential-names] [a b])]
    (filter #(not= (first %) (second %)) all-pairs)))

(defn pair-included-in-fifty-lines?
  "checks wheter a pair in more than fifty lines."
  [pair lines]
  (< 50 
     (count 
      (filter (fn [l] (and (some #(= (first pair) %) l)
                           (some #(= (second pair) %) l))) lines))))

(defn extract-pairs-appearing-fifty-times
  [candidate-pairs lines]
  (filter #(pair-included-in-fifty-lines? % lines) candidate-pairs))

(defn get-answer [source-file]
  (let [source-string (get-source-string source-file)
        lines (get-lines source-string)
        essential-names (extract-essential-names lines)
        candidate-pairs (extract-candidate-pairs essential-names)
        pairs-appearing-fifty-times (extract-pairs-appearing-fifty-times candidate-pairs lines)]
    pairs-appearing-fifty-times))
