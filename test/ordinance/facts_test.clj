(ns ordinance.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [ordinance.facts :as facts]))

(deftest lisbon-has-spec-basis
  (let [sb (facts/spec-basis "lisbon")]
    (is (= 2 (count sb)))
    (is (every? #(str/starts-with? (:ordinance/url %) "https://") sb))
    (is (every? :ordinance/number sb))))

(deftest unknown-municipality-has-no-spec-basis
  (is (nil? (facts/spec-basis "porto")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["lisbon" "porto"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["porto"] (:missing-municipalities c)))))

(deftest by-topic-filters
  (is (= 2 (count (facts/by-topic "lisbon" :governance))))
  (is (empty? (facts/by-topic "lisbon" :labor)))
  (is (empty? (facts/by-topic "porto" :governance))))
