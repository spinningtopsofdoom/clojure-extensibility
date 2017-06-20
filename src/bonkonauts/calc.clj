(ns bonkonauts.calc
  (:require
    [bonkonauts.currency :as bc]
    [bonkonauts.enemy :as be]))

;; Extend Protocol / Type

(extend-protocol bc/Bonkobucks
  bonkonauts.enemy.Fleep
  (-bonkobucks [self] 36))

(bc/-bonkobucks (be/->Fleep 10))
; => 36

(extend-type bonkonauts.enemy.Fleep
  bc/Bonkobucks
  (-bonkobucks [self] 1800))

(bc/-bonkobucks (be/->Fleep 20))
; => 1800

;; Add protocols on record creation

(defrecord Marvin [time-alive]
  bc/Bonkobucks
  (-bonkobucks [marvin]
    (* 10 (- 30 time-alive))))

(bc/-bonkobucks (->Marvin 20))
; => 100
(bc/-bonkobucks (->Marvin 10))
; => 200

;; Record Creation

(be/->Fleep 12)
; => #bonkonauts.enemy.Fleep{:hp 12}
(be/map->Fleep {:hp 12})
; => #bonkonauts.enemy.Fleep{:hp 12}
#bonkonauts.enemy.Fleep[12]
; => #bonkonauts.enemy.Fleep{:hp 12}
#bonkonauts.enemy.Fleep{:hp 12}
; => #bonkonauts.enemy.Fleep{:hp 12}

;; Using a record as a map

(get (be/->Fleep 12) :hp)
; => 12
(get (be/->Fleep 12) :speed)
; => nil
(get (assoc (be/->Fleep 12) :speed 5) :speed)
; => 5
(= (be/->Fleep 12) (be/->Fleep 12))
; => true

;; Deftype

(defprotocol EditSecret
  (-get-secret! [enemy])
  (-set-secret! [enemy new-secret]))

(deftype CustomEnemy [hp ^:unsynchronized-mutable secret]
  EditSecret
  (-get-secret! [_]
    secret)
  (-set-secret! [enemy new-secret]
    (set! secret new-secret)
    enemy))

;; Deftpe creation

#bonkonauts.calc.CustomEnemy[12 :shhhh]
; => #object[bonkonauts.bonus.CustomEnemy 0x7e276594 "bonkonauts.bonus.CustomEnemy@7e276594"]
(->CustomEnemy 12 :shhhh)
; => #object[bonkonauts.bonus.CustomEnemy 0x6bb2d00b "bonkonauts.bonus.CustomEnemy@6bb2d00b"]

;; Deftype manipulation

(def custom-enemy (->CustomEnemy 12 :shhhh))
(.hp custom-enemy)
; => 12
(.secret custom-enemy)
; => java.lang.IllegalArgumentException: No matching field found
(-get-secret! custom-enemy)
; => :shhhh
(-set-secret! custom-enemy :what?)
(-get-secret! custom-enemy)

;; Reify

(defn cubes [color bucks]
  (reify
    clojure.lang.ILookup
    (valAt [this k]
      (.valAt this k nil))
    (valAt [this k default]
      (case k
        :color color
        default))
    bc/Bonkobucks
    (-bonkobucks [_] bucks)))

;; Using reify object

(def red-cube (cubes :red 10))

(bc/-bonkobucks red-cube)
; => 10
(get red-cube :color)
; => :red
(get red-cube :hp :unknown)
; => :unknown

;; Multimethods

(defn color-map [colors]
  (let [color-num (count colors)]
    (cond
      (= 0 color-num) :zero
      (= 1 color-num) :one
      (= 2 color-num) :two
      (< 2 color-num 5) :multi
      (>= color-num 5) :rainbow)))

(defmulti show-colors color-map)

(defmethod show-colors :zero [colors]
  "No colors to show you")
(show-colors [])
; => "No colors to show you"
(defmethod show-colors :one [colors]
  (str "Monochromatic " (first colors) "."))
(show-colors [:red])
; => "Monochromatic :red."

(show-colors [:red :orange])
; => java.lang.IllegalArgumentException: No method in multimethod 'show-colors' for dispatch value: :two
(defmethod show-colors :default [colors]
  (str "There's these colors " colors))
(show-colors [:red :orange])
; => "There's these colors [:red :orange]"
(defmethod show-colors :two [colors]
  (str "Show a " (first colors) " and " (second colors) " picture"))
(show-colors [:red :orange])
; => "Show a :red and :orange picture"

(defmethod show-colors :multi [colors]
  (str "Show colorfulness of (" (apply str (interpose ", " colors)) ")"))
(show-colors [:red :orange :black :violet])
; => "Show colorfulness of (:red, :orange, :black, :violet)"
(defmethod show-colors :rainbow [colors]
  (str "The rainbow of colors (" (apply str (interpose ", " colors)) ")"))
(show-colors [:red :orange :black :violet :green])
; => "The rainbow of colors (:red, :orange, :black, :violet, :green)"