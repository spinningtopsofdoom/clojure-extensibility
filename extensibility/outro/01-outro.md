!SLIDE

# Clojure Extension Points
* `defrecord` and `defprotocol` normal (application level) uses
* `deftype` for programming language level data types
* `reify` creates anonymous types
* `defmulti` and `defmethod` create an open function that dispatches on it's inputs

!SLIDE

# Clojure Extension Points provide great power
## Data > Function > Protocol and the best protocol has zero methods

!SLIDE

# Reasons to use Clojure's Extensibility
* Lots of different data types (e.g. Java JDBC)
* Interactions with closed third party libraries
* System of custom components (e.g. Component, Om, Reagent)
* Integrate data type into core Clojure functions (e.g. `clojure.data.avl`)
* New abstraction that can not be built on top of current abstractions
* More performant abstraction (e.g. diffing a data structure)

!SLIDE

# Thanks
