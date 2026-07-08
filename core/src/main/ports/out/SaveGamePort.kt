package hwr.oop.examples.dominion.ports.out

import hwr.oop.examples.core.Game

interface SaveGamePort {

    fun save(game: Game): Unit

}
