;; tools/gen_tx.clj — regenerates data/datascript-tx.edn from
;; `ordinance.facts/catalog`. data/ is a projection, never hand-edited:
;; run `clojure -M -i tools/gen_tx.clj` after changing the catalog.
(require '[ordinance.facts :as facts]
         '[clojure.pprint :as pp])

(def rows
  (mapv (fn [o] (-> o
                    (update :ordinance/topic #(vec (sort %)))))
        (facts/spec-basis "lisbon")))

(spit "data/datascript-tx.edn"
      (str ";; data/datascript-tx.edn — DataScript tx-data derived from src/ordinance/facts.cljc\n"
           ";; `ordinance.facts/catalog` (ADR-2607141700). Same shape as sibling files.\n"
           ";;\n"
           ";; GENERATED — do not hand-edit. Regenerate with:\n"
           ";;   clojure -M -i tools/gen_tx.clj\n"
           ";; `test/ordinance/facts_test.clj` fails if this file drifts from the catalog.\n\n"
           (with-out-str (pp/pprint rows))))
(println "wrote data/datascript-tx.edn —" (count rows) "entries")
