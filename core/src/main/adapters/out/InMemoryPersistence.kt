package hwr.oop.examples.adapters.out

import hwr.oop.examples.core.Game
import hwr.oop.examples.core.GameId
import hwr.oop.examples.out.LoadGameByIdPort
import hwr.oop.examples.ports.out.SaveGamePort

internal class InMemoryPersistence : LoadGameByIdPort, SaveGamePort {

    private val map = mutableMapOf<GameId, Game>()

    override fun save(game: Game) {
        val id = game.id()
        map[id] = game
    }

    override fun loadByid(gameId: GameId): Game =
        map[gameId] ?: throw LoadGameByIdPort.CouldNotLoadException(gameId)
}