


;;;;;;;
(comment 
  (def source-file "Artist_lists_small_tester.txt")
  (def source-string (time (get-source-string source-file)))
  )


(comment (def lines (time (get-lines source-string)))
         (first lines)
         (type lines)
         )



(def lines-sample (take 300 lines))
(first lines-sample)
(def names-sample (extract-candid-names lines-sample))
(first names-sample)



(def pairs-sample (generate-pairs names-sample))
(first pairs-sample)


lines-sample
pairs-sample



(keys
 (filter
  #(<= 20 (val %))
  (apply
   merge-with +
   (filter
    some?
    (mapcat
     (fn [line]
       (map
        (fn [pair]
          (when (and (some #(= (first pair) %) line)
                     (some #(= (second pair) %) line))
            {pair 1}))
        pairs-sample))
     lines-sample)
    )
   )))



(count (narrow-pairs pairs-sample lines-sample 20))
