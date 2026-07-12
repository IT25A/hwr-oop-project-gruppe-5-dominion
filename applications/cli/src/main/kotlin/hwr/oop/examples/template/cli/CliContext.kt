package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.obj
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.ports.out.GameRepository

data class CliContext(val persistence: GameRepository, val gameId: GameID) {
    fun loadGame(): GameInstance {
        return persistence.loadByid(gameId)
    }

    fun saveGame(instance: GameInstance) {
        persistence.save(instance)
    }
}