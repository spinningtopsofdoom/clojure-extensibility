!SLIDE

# Clojure's Solution
## Polymorphism ala carte

"100 functions that operate on abstraction" - Transliterated Alan Perlis quote

!SLIDE
# Inspired by Haskell TypeClasses
## Three components
* Protocol (Abstraction similar to a Java Interface)
* Record (Data Type think of a class)
* Concrete Implementation of a Protocol and Record

!SLIDE

# Protocol (Bonkobucks Abstraction)

    @@@clojure
    (ns bonkonauts.currency)

    (defprotocol Bonkobucks
      (-bonkobucks [enemy]))

!SLIDE

# Record (Fleep Class / Type)

    @@@clojure
    (ns bonkonauts.enemy)

    (defrecord Fleep [hp])

!SLIDE

# Fleep Bonkobucks (concrete implementation)

    @@@clojure
    (ns bonkonauts.calc
      (:require
        [bonkonauts.currency :as bc]
        [bonkonauts.enemy :as be]))

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

!SLIDE

# We can add protocols at record creation

    @@@clojure
    (defrecord Marvin [time-alive]
      bc/Bonkobucks
      (-bonkobucks [marvin]
        (* 10 (- 30 time-alive))))

    (bc/-bonkobucks (->Marvin 20))
    ; => 100
    (bc/-bonkobucks (->Marvin 10))
    ; => 200

!SLIDE

# `-bonkobucks` dispatches on the type of it's first argument
## Similar to a hard coded case statement

    @@@clojure
    (defn case-bonkobucks [enemy]
      (condp = (class enemy)
        bonkonauts.enemy.Fleep (fleep-bucks enemy)
        bonkonauts.calc.Marvin (marvin-bucks enemy)))

!SLIDE

## Anatomy of a protocol

    @@@clojure
    (protocol-fn [calling-object arg1 arg2 arg3 arg4 ...])

!SLIDE

# Things to note
* `-bonkobucks` is fully namespaced (`bonkonauts.currency.-bonkobucks`)
* Call reversal `(-bonkobucks fleep)` as opposed to `(fleep -bonkobucks)`
* First argument of a protocol is always the calling type

!SLIDE

# Extending to Clojure Data types

    @@@clojure
    (extend-protocol bc/Bonkobucks
      clojure.lang.PersistentArrayMap
      (-bonkobucks [enemy] (get enemy :bucks 1))
      clojure.lang.PersistentHashMap
      (-bonkobucks [enemy] (get enemy :bucks 1)))

    (bc/-bonkobucks {:bucks 13})
    ; => 13
    (bc/-bonkobucks {:name "Bugs"})
    ; => 1
