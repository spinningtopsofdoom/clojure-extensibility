(ns bonkonauts.bonus)

(deftype COWVector [arr])

(defn cow-vector [& more]
  (->COWVector (object-array more)))