!SLIDE

# `defrecord` and `deftype`
## Application logic and programming constructs

!SLIDE

# Fleep Record Creation

    @@@clojure
    (be/->Fleep 12)
    ; => #bonkobucks.enemy.Fleep{:hp 12}
    (be/map->Fleep {:hp 12})
    ; => #bonkobucks.enemy.Fleep{:hp 12}
    #madison.meetup.vectors.Fleep[12]
    ; => #bonkobucks.enemy.Fleep{:hp 12}
    #bonkobucks.enemy.Fleep{:hp 12}
    ; => #bonkobucks.enemy.Fleep{:hp 12}

!SLIDE

# Fleep Record Operations

    @@@clojure
    (get (->Fleep 12) :hp)
    ; => 12
    (get (->Fleep 12) :speed)
    ; => nil
    (get (assoc (->Fleep 12) :speed 5) :speed)
    ; => 5
    (= (->Fleep 12) (->Fleep 12))
    ; => true

!SLIDE

# `defrecord` gives you
* Positional `->Fleep` and associative `map->Fleep` builder functions
* Pretty printed output
* A reader literal for `Fleep` that takes a map
* Associative (map) support
* Value equality and hash support
* Immutable fields


!SLIDE

# `defrecord` is primarily for application logic
## It gives a rich map like data type that acts like a Clojure map

Eschews data hiding and mini languages for every data type. Opts for ease of data access and seamless usability with core functions.

!SLIDE

# `deftype` creates bare data type
## Used for making programming constructs

!SLIDE

# `deftype` example

    @@@clojure
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

!SLIDE

# `deftype` creation

    @@@clojure
    #bonkonauts.calc.CustomEnemy[12 :shhhh]
    ; => #object[bonkonauts.bonus.CustomEnemy 0x7e276594 "bonkonauts.bonus.CustomEnemy@7e276594"]
    (->CustomEnemy 12 :shhhh)
    ; => #object[bonkonauts.bonus.CustomEnemy 0x6bb2d00b "bonkonauts.bonus.CustomEnemy@6bb2d00b"]

!SLIDE

# `deftype` manipulation

    @@@clojure
    (def custom-enemy (->CustomEnemy 12 :shhhh))
    (.hp custom-enemy)
    ; => 12
    (.secret custom-enemy)
    ; => java.lang.IllegalArgumentException: No matching field found
    (-get-secret! custom-enemy)
    ; => :shhhh
    (-set-secret! custom-enemy :what?)
    (-get-secret! custom-enemy)
    ; => :what?

!SLIDE

# `deftype` gives you
* Positional `->CustomEnemy` builder functions
* A reader literal for `CustomEnemy` that takes a vector
* Ability to have mutable fields

!SLIDE

# Use `deftype` for language level constructs

## For examples see
* [Avl Tree](https://github.com/clojure/data.avl), [RRB Vector](https://github.com/clojure/core.rrb-vector)
* ClojureScript (all data types are created with `deftype`)