(ns suffix-tree.core
  (:require [clojure.zip :as z]
            [clojure.pprint :refer [pprint]]))

(defn- get-child-branch [loc value]
  (loop [cloc (z/down loc)]
    (let [child (z/node cloc)]
      (if (= (and (vector? child) ((z/node cloc) 0)) value)
        cloc
        (recur (z/right cloc))))))

(defn create-suffix-tree [input]
  (let [invec (if (vector? input) input (into [] input))
        lenvec (count invec)]
    (loop [i 0
           root (z/zipper vector? seq (fn [_ c] (into [] c)) [])]
      (if (= i lenvec)
        (z/node root)
        (recur 
         (inc i) 
         (z/zipper vector? seq (fn [_ c] (into [] c)) 
                   (z/root 
                    (loop [n i
                           loc root]
                      (if (= n lenvec)
                        (z/append-child loc i)
                        (let [c (invec n)]
                          (if (some #(and (vector? %) (= c (% 0))) (z/children loc))
                            (recur (inc n) (get-child-branch loc c))
                            (recur (inc n) 
                                   (-> loc (z/append-child [c]) (z/down) 
                                       (z/rightmost))))))))))))))
