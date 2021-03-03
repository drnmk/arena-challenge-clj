(ns arena-challenge-clj.functions
  (:require [clojure.java.io :as io]
            [clojure.string :as cstr]))

;;;; all functions in this namespace is pure functions ;;;;

(defn get-source-string
  "using the filename in string,
  source in the given file
  is captured in string."
  [source-file]
  (-> source-file 
      io/resource
      io/file
      slurp))

(defn get-lines
  "using the given source string,
  lines are parsed as collection of line items.
  each line has multiple names."
  [source-string]
  (as-> source-string v
    (cstr/split v #"\n")
    (map #(cstr/split % #",") v)))

(defn extract-candid-names
  "in order to be a part of more-than-fifty pairs,
  individual name must also appear at least fifty times.
  therefore, this function extracts all individual names
  that appear more than fifty times by itself.
  for xflexibility, the threshold for fifty is given
  as an argument."
  [lines threshold]
  (->> lines
       (apply concat)
       frequencies
       (filter #(<= threshold (val %)))
       keys))

(defn generate-pairs
  "using all names each of which appears more than
  threshold time, it generates unique pairs."
  [names]
  (distinct (for [a names b names
                  :when (not= a b)]
              #{a b})))

(defn narrow-pairs
  "using the candidate pairs, lines, and threshold,
  this function detects how many lines
  each candidate pair appears in.
  map is used to calculate the number of appearances.
  in other words, the pair itself is the key and
  counted number of appearances is its value.
  once the appeances are counted,
  the whole map gets merged by summing the counts."
  [pairs lines threshold]
  (keys
   (filter #(<= threshold (val %))
           (apply merge-with +
                  (filter some?
                          (mapcat
                           (fn [line]
                             (map
                              (fn [pair]
                                (when (and (some #(= (first pair) %) line)
                                           (some #(= (second pair) %) line))
                                  {pair 1}))
                              pairs))
                           lines))))))

(defn process
  "this is a complete process cycle
  containing all functions above.
  using let statement, it leaves clues of
  what process is made at which stage."
  [source-file threshold]
  (let [source-string (get-source-string source-file)
        lines (get-lines source-string)
        names (extract-candid-names lines threshold)
        pairs (generate-pairs names)
        extracts (narrow-pairs pairs lines threshold)]
    extracts))
