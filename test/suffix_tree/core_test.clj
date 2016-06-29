(ns suffix-tree.core-test
  (:require [clojure.test :refer :all]
            [suffix-tree.core :refer :all]))

(deftest build-tree-0
  (testing "testing with ab"
    (is (= [[\a [\b 0]] [\b 1]])
        (create-suffix-tree (into [] "ab")))))

(deftest build-tree-1
  (testing "testing with 1234"
    (is (= [[\1 [\2 [\3 [\4 0]]]] [\2 [\3 [\4 1]]] [\3 [\4 2]] [\4 3]]
           (create-suffix-tree (into [] "1234"))))))

(deftest build-tree-2
  (testing "testing with abbab"
    (is (= [[\a [\b [\b [\a [\b 0]]] 3]] [\b [\b [\a [\b 1]]] [\a [\b 2]] 4]]
           (create-suffix-tree "abbab")))))

(deftest build-tree-3
  (testing "testing with []"
    (is (= [] (create-suffix-tree [])))))

(deftest build-tree-4
  (testing "testing with ''"
    (is (= [] (create-suffix-tree "")))))
