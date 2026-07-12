package hwr.oop.examples.dominion.GamePhases

import hwr.oop.examples.dominion.GamePhase
import hwr.oop.examples.dominion.PlayerId

class DominionFinishedGame(override val winner: PlayerId) : GamePhase.Finished {
    override fun toString() = "DominionFinishedGame"

    override fun nextPhase(): GamePhase {
        throw IllegalStateException("Game has been finished! Thank u for playing our game")
    }
}