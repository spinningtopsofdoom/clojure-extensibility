!SLIDE

# Extensibility bonus round
* Anonymous data type creation (`reify`)
* Functions that dispatch based on arguments (`defmulti`)

!SLIDE

# `reify`
## Create anonymous data types

    @@@clojure
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

!SLIDE

# `reify` object acts exactly like a `deftype` object

    @@@clojure
    (def red-cube (cubes :red 10))

    (bc/-bonkobucks red-cube)
    ; => 10
    (get red-cube :color)
    ; => :red
    (get red-cube :hp :unknown)
    ; => :unknown

!SLIDE

# `reify` use cases similar to anonymous functions
* Dynamic context (like React components)
* Functionality dependent on object data
* Not important enough to name

!SLIDE

# Multi Methods
## Functions that dispatch based on their arguments

!SLIDE

# `show-color` multi method

	@@@clojure
	(defn color-map [colors]
	  (let [color-num (count colors)]
		(cond
		  (= 0 color-num) :zero
		  (= 1 color-num) :one
		  (= 2 color-num) :two
		  (< 2 color-num 5) :multi
		  (>= color-num 5) :rainbow)))

	(defmulti show-colors color-map)

!SLIDE

# `show-colors` dispatch is open

	@@@clojure
	(defmethod show-colors :zero [colors]
	  "No colors to show you")
	(show-colors [])
	; => "No colors to show you"
	(defmethod show-colors :one [colors]
	  (str "Monochromatic " (first colors) "."))
	(show-colors [:red])
	; => "Monochromatic :red."


!SLIDE

## Anatomy of a multi method

    @@@clojure
    (defmulti multi-fn-name dispacth-function)

    (defmethod multi-fn-name dispatch-function-result-1 [params])
    (defmethod multi-fn-name dispatch-function-result-2 [params])

!SLIDE

# A default mutli method can be set

	@@@clojure
	 (show-colors [:red :orange])
	; => java.lang.IllegalArgumentException:
    ; => No method in multimethod 'show-colors' for dispatch value: :two
	(defmethod show-colors :default [colors]
	  (str "There's these colors " colors))
	(show-colors [:red :orange])
	; => "There's these colors [:red :orange]"
	(defmethod show-colors :two [colors]
	  (str "Show a " (first colors) " and " (second colors) " picture"))
	(show-colors [:red :orange])
	; => "Show a :red and :orange picture"

!SLIDE

## Like `defprotocol` `defmulti` can be simulated with a `cond`

	@@@clojure
    (defn case-show-colors [colors]
      (condp = (color-map colors)
        :zero (empty-colors colors)
        :one (one-color colors)
        :two (two-color colors)
        (default-show-colors colors)))

!SLIDE

# Common use cases
* Different functions for a map (e.g. HTTP response map)
* Building a open system not dependent on types
