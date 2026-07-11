package hwr.oop.examples.dominion.ports.out
import hwr.oop.examples.dominion.GameInstance


interface SaveGamePort {
    fun save(game: GameInstance): Unit
}