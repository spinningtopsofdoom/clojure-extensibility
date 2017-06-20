!SLIDE
# Common Solutions

## From Java, JavaScript, Python, Ruby, or PHP

!SLIDE
# Add method to class
## Java

    @@@java
    class Fleep {
      public bonkobucks() {
          /* */
      }
    }

!SLIDE

## Caveats
* Need access to source code
  * Not possible for third party libraries
* Can only be added at class definition site
* All cross cutting concerns will be added to all classes

!SLIDE

# Wrapper
## Python

    @@@python
    class FleepBucks:
      def __init__(self, fleep):
          this.fleep = fleep
    def bonkobucks(self):
        pass

!SLIDE

## Caveats
* Two different classes for the "same" object
  * `fleep != fleep_bucks`
* Lack of composition
  * How to combine `FleepBucks`, `FleepHp`, `FleepHostility`

!SLIDE

# Monkey Patching
## Ruby

	@@@ruby
	class Fleep
	  def bonkobucks:

!SLIDE

## Caveats
* Name collisions
* No source of truth
* Order dependent (which patch was called first)
