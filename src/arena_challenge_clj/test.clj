(ns arena-challenge-clj.test
  (:require [arena-challenge-clj.functions :refer :all]))

;; check get-source-string function
(= java.lang.String
   (type (get-source-string "Artist_lists_small.txt")))
;; => true

;; check get-lines function 
(= '(["U2" "Daft Punk" "Moby"] ["B.B.King" "DJ Shadow" "Moby"])
   (get-lines "U2,Daft Punk,Moby\nB.B.King,DJ Shadow,Moby"))
;; => true

;; check extract-candid-names function
;; using threshold number, 2 
(= '("U2" "Moby")
   (extract-candid-names [["U2" "Daft Punk" "Moby"]
                          ["U2" "DJ Shadow" "Moby"]] 2))
;; => true

;; check generate-pairs function
(= '(#{"Daft Punk" "U2"} #{"Moby" "U2"} #{"Moby" "Daft Punk"})
   (generate-pairs ["U2" "Daft Punk" "Moby" "U2"]))
;; => true

;; check narrow-pairs function 
(= '(#{"Moby" "U2"})
   (narrow-pairs '(#{"Daft Punk" "U2"} #{"Moby" "U2"} #{"Moby" "Daft Punk"})
                 '(["Daft Punk" "U2" "Moby" "BTS"] ["Madonna" "U2" "Moby"])
                 2))
;; => true
