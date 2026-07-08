package hwr.oop.examples.dominion.ports.out

import hwr.oop.examples.core.Game
import hwr.oop.examples.core.GameId

interface LoadGameByIdPort {

    fun loadByid(gameId: GameId): Game

    class CouldNotLoadException(
        gameId: GameId,
        cause: Exception? = null
    ) : RuntimeException(
        "Could not load game with id: $gameId",
        cause
    )

}