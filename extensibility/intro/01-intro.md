!SLIDE

# Clojure Extensibility
## Ala carte Polymorphism

&nbsp;
## Peter Schuck
## @spinningtopsofdoom

!SLIDE
## Bonkonauts

### A game about astronauts bonking on the head aliens with various implements (bats, oversized hammers, ???).

!SLIDE
# New Feature
## In Game Currency (Bonkobucks)

Whenever an enemy is defeated you receive a certain amount of Bonkobucks.  We want to somehow add this to the `Fleep` enemy

    @@@clojure
    (bonkobucks ???)

!SLIDE

## Hooks for Bonkobucks
* Testing (having a maximum amount a player can receive in an hour)
* Analytics (what does the Bonkobucks distribution over a day look like)
* Machine Learning (do players go after higher paying enemies)
* Level design (only a certain amount of Bonkobucks is allowed per level)

